package com.ecommerce.shopping_cart.repository;

import com.ecommerce.shopping_cart.domain.AccountStatus;
import com.ecommerce.shopping_cart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus accountStatus);
}
