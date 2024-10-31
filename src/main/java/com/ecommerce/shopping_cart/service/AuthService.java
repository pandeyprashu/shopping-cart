package com.ecommerce.shopping_cart.service;

import com.ecommerce.shopping_cart.response.SignupRequest;

public interface AuthService {

    String createUser(SignupRequest request);
}
