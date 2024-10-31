package com.ecommerce.shopping_cart.service.impl;

import com.ecommerce.shopping_cart.config.JwtProvider;
import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.ecommerce.shopping_cart.model.Cart;
import com.ecommerce.shopping_cart.model.User;
import com.ecommerce.shopping_cart.repository.CartRepository;
import com.ecommerce.shopping_cart.repository.UserRepository;
import com.ecommerce.shopping_cart.response.SignupRequest;
import com.ecommerce.shopping_cart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String createUser(SignupRequest request) {
        User user =userRepository.findByEmail(request.getEmail());

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
}
