package com.ecommerce.shopping_cart.response;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String fullName;
    private String otp;
}
