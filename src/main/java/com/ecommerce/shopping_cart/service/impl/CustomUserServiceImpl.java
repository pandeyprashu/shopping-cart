package com.ecommerce.shopping_cart.service.impl;

import com.ecommerce.shopping_cart.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private static final String SELLER_PREFIX="seller_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.startsWith(SELLER_PREFIX)){
            // if seller login
        }else{

        }



        return null;
    }
}
