package com.ecommerce.shopping_cart.service.impl;

import com.ecommerce.shopping_cart.config.JwtProvider;
import com.ecommerce.shopping_cart.model.User;
import com.ecommerce.shopping_cart.repository.UserRepository;
import com.ecommerce.shopping_cart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
       User user=userRepository.findByEmail(email);
       if(user==null){
           throw new Exception("User not found : "+email);
       }
        return user;
    }
}
