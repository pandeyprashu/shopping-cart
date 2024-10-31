package com.ecommerce.shopping_cart.repository;

import com.ecommerce.shopping_cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
