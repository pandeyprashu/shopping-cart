package com.ecommerce.shopping_cart.controller;

import com.ecommerce.shopping_cart.model.User;
import com.ecommerce.shopping_cart.response.AuthResponse;
import com.ecommerce.shopping_cart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> findUserHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);

    }
}
