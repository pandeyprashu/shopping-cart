package com.ecommerce.shopping_cart.model;

import lombok.Data;

import java.util.List;

@Data
public class Home {

    private List<HomeCategory> grid;
    private List<HomeCategory> shopByCategory;
    private List<HomeCategory> electricCategories;
    private List<HomeCategory> dealCategories;
    private List<Deal> deals;

    // <10>
}
