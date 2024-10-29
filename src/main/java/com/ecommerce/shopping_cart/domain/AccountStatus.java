package com.ecommerce.shopping_cart.domain;

public enum AccountStatus {

    PENDING_VERIFICATION,   //Account is created but not verified
    ACTIVE,                //Account is active
    BANNED,                //Account is banned
    CLOSED,
    DEACTIVATED,
    SUSPENDED

}
