package com.ecommerce.shopping_cart.repository;


import com.ecommerce.shopping_cart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
