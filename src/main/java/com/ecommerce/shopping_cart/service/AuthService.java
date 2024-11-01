package com.ecommerce.shopping_cart.service;

import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.ecommerce.shopping_cart.request.LoginRequest;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.response.SignupRequest;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest request);
    //for login
    AuthResponse SignIn(LoginRequest req);

}
