package com.ecommerce.shopping_cart.service.impl;

import com.ecommerce.shopping_cart.config.JwtProvider;
import com.ecommerce.shopping_cart.domain.AccountStatus;
import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.ecommerce.shopping_cart.model.Address;
import com.ecommerce.shopping_cart.model.Seller;
import com.ecommerce.shopping_cart.repository.AddressRepository;
import com.ecommerce.shopping_cart.repository.SellerRepository;
import com.ecommerce.shopping_cart.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        System.out.println(email);


        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExists = sellerRepository.findByEmail(seller.getEmail());
        if(sellerExists!=null){
            throw new Exception("Email already exists");
        }
        Address address=addressRepository.save(seller.getPickupAddress());
        Seller newSeller=new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(address);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setBusinessDetails(seller.getBusinessDetails());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setMobile(seller.getMobile());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(()->new RuntimeException("Seller not found"));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        System.out.println(email    );
        Seller seller = sellerRepository.findByEmail(email);
        if(seller==null){
            throw new Exception("Seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus accountStatus) {
        return sellerRepository.findByAccountStatus(accountStatus);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        Seller existingSeller=sellerRepository.findById(id).orElseThrow(()->new RuntimeException("Seller not found"));

        if(seller.getSellerName()!=null){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getEmail()!=null){
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getMobile()!=null){
            existingSeller.setMobile(seller.getMobile());
        }
        if(seller.getBusinessDetails()!=null&&seller.getBusinessDetails().getBusinessName()!=null){
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if(seller.getBankDetails()!=null&&seller.getBankDetails().getAccountNumber()!=null
                &&seller.getBankDetails().getIfscCode()!=null
                &&seller.getBankDetails().getAccountHolderName()!=null){

            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());

        }
        if(seller.getPickupAddress()!=null){
            existingSeller.setPickupAddress(seller.getPickupAddress());
        }

        if(seller.getGSTIN()!=null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }
        return sellerRepository.save(existingSeller);


    }

    @Override
    public void deleteSeller(Long id) {

        Seller seller=getSellerById(id);
        sellerRepository.delete(seller);

    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller=getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long id, AccountStatus accountStatus) {
        Seller seller=getSellerById(id);
        seller.setAccountStatus(accountStatus);

        return sellerRepository.save(seller);

    }
}
