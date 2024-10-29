package com.ecommerce.shopping_cart.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private int mrpPrice;

    private int sellingPrice;

    private int discountPercent;

    private int quantity;

    private String color;

    @ElementCollection
    private List<String> images=new ArrayList<>();

    private int numRatings;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Seller seller;

    private LocalDate createdAt;

    private String sizes;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews=new ArrayList<>();

}
