package com.ecommerce.shopping_cart.repository;

import com.ecommerce.shopping_cart.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
