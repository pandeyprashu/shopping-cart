package com.ecommerce.shopping_cart.controller;

import com.ecommerce.shopping_cart.config.JwtProvider;
import com.ecommerce.shopping_cart.domain.AccountStatus;
import com.ecommerce.shopping_cart.model.Seller;
import com.ecommerce.shopping_cart.model.VerificationCode;
import com.ecommerce.shopping_cart.repository.VerificationCodeRepository;
import com.ecommerce.shopping_cart.request.LoginRequest;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.service.AuthService;
import com.ecommerce.shopping_cart.service.EmailService;
import com.ecommerce.shopping_cart.service.SellerService;
import com.ecommerce.shopping_cart.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req){
        String otp=req.getOtp();
        String email=req.getEmail();
        req.setEmail("seller_"+email);
        System.out.println("OTP:"+otp);

        AuthResponse authResponse=authService.SignIn(req);
        authResponse.setMessage("Login Successfully");
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {
        VerificationCode verificationCode=verificationCodeRepository.findByOtp(otp);

        if(verificationCode==null||!verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong OTP");
        }
        Seller seller=sellerService.verifyEmail(verificationCode.getEmail(),otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
        Seller savedSeller=sellerService.createSeller(seller);
        String otp= OtpUtil.generateOtp();
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject="Ecommerce Verify Your Seller Email";
        String text="Welcome to Ecommerce, Verify your account using link:";
        String frontEndUrl="http://localhost:3000/seller-verify/";
        emailService.sendVerificationOtpEmail(seller.getEmail(),otp,subject,text+frontEndUrl);
        return  new ResponseEntity<>(savedSeller,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id){
        Seller seller=sellerService.getSellerById(id);
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller=sellerService.getSellerByEmail(email);

        return new ResponseEntity<>(seller,HttpStatus.OK);

    }

//    @GetMapping("/report")
//    public ResponseEntity<SellerReport> getSellerReport(
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//        String email=jwtProvider.getEmailFromJwtToken(jwt);
//        Seller seller=sellerService.getSellerByEmail(email);
//        return new ResponseEntity<>(new SellerReport(seller),HttpStatus.OK);
//
//    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus accountStatus){
        List<Seller> sellers=sellerService.getAllSellers(accountStatus);
        return new ResponseEntity<>(sellers,HttpStatus.OK);

    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization")String jwt,
                                               @RequestBody Seller seller) throws Exception{
       Seller profile =sellerService.getSellerProfile(jwt);
       Seller updatedSeller=sellerService.updateSeller(profile.getId(),seller);
       return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Long id){
        sellerService.deleteSeller(id);
        return new ResponseEntity<>("Seller Deleted Successfully",HttpStatus.OK);
    }



}
