package com.ecommerce.shopping_cart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    //any changes in cartItems will be reflected in cart
    //if cart is deleted all cartItems will be deleted
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int totalItems;

    private int totalMrpPrice;

    private int discount;

    private String couponCode;
}
