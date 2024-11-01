package com.ecommerce.shopping_cart.repository;

import com.ecommerce.shopping_cart.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByEmail(String email);
}