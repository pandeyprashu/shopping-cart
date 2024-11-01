package com.ecommerce.shopping_cart.service;

import com.ecommerce.shopping_cart.model.User;

public interface UserService {

     User findUserByJwtToken(String jwt) throws Exception;

     User findUserByEmail(String email) throws Exception;

}
