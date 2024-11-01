package com.ecommerce.shopping_cart.controller;

import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.ecommerce.shopping_cart.model.User;
import com.ecommerce.shopping_cart.model.VerificationCode;
import com.ecommerce.shopping_cart.repository.UserRepository;
import com.ecommerce.shopping_cart.request.LoginRequest;
import com.ecommerce.shopping_cart.response.APIResponse;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.response.SignupRequest;
import com.ecommerce.shopping_cart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest signupRequest){
        String jwt = authService.createUser(signupRequest);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("User Created Successfully");
        authResponse.setUserRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(authResponse);

    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<APIResponse> sentOtpHandler(@RequestBody VerificationCode req){

        authService.sentLoginOtp(req.getEmail());
        APIResponse authResponse=new APIResponse();
        authResponse.setMessage("OTP Sent Successfully");
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> LoginHandler(@RequestBody LoginRequest req){

       AuthResponse authResponse= authService.SignIn(req);

       return ResponseEntity.ok(authResponse);
    }
}
