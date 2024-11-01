package com.ecommerce.shopping_cart.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String otp;
}
