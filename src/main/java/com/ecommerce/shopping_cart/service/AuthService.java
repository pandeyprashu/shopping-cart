package com.ecommerce.shopping_cart.service;

import com.ecommerce.shopping_cart.request.LoginRequest;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email);
    String createUser(SignupRequest request);
    //for login
    AuthResponse SignIn(LoginRequest req);
}
