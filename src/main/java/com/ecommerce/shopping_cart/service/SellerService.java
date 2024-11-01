package com.ecommerce.shopping_cart.service;

import com.ecommerce.shopping_cart.domain.AccountStatus;
import com.ecommerce.shopping_cart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id);
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus accountStatus);
    Seller updateSeller(Long id,Seller seller);
    void deleteSeller(Long id);
    Seller verifyEmail(String email,String otp) throws Exception;
    Seller updateSellerAccountStatus(Long id,AccountStatus accountStatus);

}
