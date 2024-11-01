package com.ecommerce.shopping_cart.request;

import com.ecommerce.shopping_cart.domain.USER_ROLE;
import lombok.Data;

@Data
public class LoginOtpRequest {
    private String email;
    private String otp;
    private USER_ROLE role;
}
