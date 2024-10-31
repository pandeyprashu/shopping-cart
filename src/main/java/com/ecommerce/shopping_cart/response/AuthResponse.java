package com.ecommerce.shopping_cart.response;

import com.ecommerce.shopping_cart.domain.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE userRole;
}
