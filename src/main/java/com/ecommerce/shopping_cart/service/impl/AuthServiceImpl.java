package com.ecommerce.shopping_cart.service.impl;

import com.ecommerce.shopping_cart.config.JwtProvider;
import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.ecommerce.shopping_cart.model.Cart;
import com.ecommerce.shopping_cart.model.Seller;
import com.ecommerce.shopping_cart.model.User;
import com.ecommerce.shopping_cart.model.VerificationCode;
import com.ecommerce.shopping_cart.repository.CartRepository;
import com.ecommerce.shopping_cart.repository.SellerRepository;
import com.ecommerce.shopping_cart.repository.UserRepository;
import com.ecommerce.shopping_cart.repository.VerificationCodeRepository;
import com.ecommerce.shopping_cart.request.LoginRequest;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.response.SignupRequest;
import com.ecommerce.shopping_cart.service.AuthService;
import com.ecommerce.shopping_cart.service.EmailService;
import com.ecommerce.shopping_cart.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;

    @Override
    public void sentLoginOtp(String email,USER_ROLE role) throws Exception {
        String SIGNING_PREFIX="signing_";


        if(email.startsWith(SIGNING_PREFIX)) {
            email = email.substring(SIGNING_PREFIX.length());
        }


        if(role.equals(USER_ROLE.ROLE_SELLER)){
            Seller seller=sellerRepository.findByEmail(email);
            if(seller==null){
                throw new Exception("Seller not found");
            }

        }else{
            User user=userRepository.findByEmail(email);
            if(user==null){
                throw new Exception("User not found");
            }
        }


            VerificationCode isExist=verificationCodeRepository.findByEmail(email);
            System.out.println(isExist);
            if(isExist!=null){
                verificationCodeRepository.delete(isExist);
            }

            String otp= OtpUtil.generateOtp();
            VerificationCode verificationCode=new VerificationCode();
            verificationCode.setOtp(otp);
            verificationCode.setEmail(email);
            verificationCodeRepository.save(verificationCode);
            System.out.println("OTP IS GENERATED: "+otp);

            //TODO send email with otp
            String subject="Ecommerce Login/Signup OTP";
            String text="Your Login/SignUp OTP is : "+otp+"\nThank & Regards\nPrashu Pandey";
            try {
                emailService.sendVerificationOtpEmail(email,otp,subject,text);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }



    }

    @Override
    public String createUser(SignupRequest request) {
        User user =userRepository.findByEmail(request.getEmail());



        VerificationCode verificationCode=verificationCodeRepository.findByEmail(request.getEmail());

        if(verificationCode==null||!verificationCode.getOtp().equals(request.getOtp())){
            try {
                throw new Exception("Wrong OTP");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(user==null){
            User createdUser= new User();
            createdUser.setEmail(request.getEmail());
            createdUser.setFullName(request.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("XXXX-XXX-XXX");
            createdUser.setPassword(passwordEncoder.encode(request.getOtp()));

            user=userRepository.save(createdUser);
            Cart cart =new Cart();
            cart.setUser(user);
            cartRepository.save(cart);


        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication=new UsernamePasswordAuthenticationToken(request.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse SignIn(LoginRequest req) {
        String username=req.getEmail();
        String otp=req.getOtp();


        Authentication authentication=authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //create jwt token
        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login Successfully");

        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
        String roleName=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setUserRole(USER_ROLE.valueOf(roleName));
        return authResponse;

    }

    //verify the OTP
    private Authentication authenticate(String username, String otp) {
//        System.out.println(username);
//        username=username.substring(7);
//        System.out.println(username);
        UserDetails userDetails=customUserService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }

        //because email is coming seller prefix + email
        if(username.startsWith("seller_")){
            username=username.substring(7);
        }
        VerificationCode verificationCode=verificationCodeRepository.findByEmail(username);
        if(verificationCode==null||!verificationCode.getOtp().equals(otp)){
            System.out.println(otp);
            throw new BadCredentialsException("Invalid OTP");
        }

        System.out.println("Sab Check hogya ");


        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
