package com.ecommerce.shopping_cart.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //one user can have multiple transactions
    @ManyToOne
    private User customer;

    @OneToOne
    private Order order;

    @ManyToOne
    private Seller seller;

    private LocalDateTime transactionDate=LocalDateTime.now();
}
