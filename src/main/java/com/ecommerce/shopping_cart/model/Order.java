package com.ecommerce.shopping_cart.model;


import com.ecommerce.shopping_cart.domain.OrderStatus;
import com.ecommerce.shopping_cart.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderId;

    @ManyToOne
    private User user;

    private Long sellerId;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems=new ArrayList<>(); // <OrderItem>

    @ManyToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails=new PaymentDetails();

    private double totalMrpPrice;

    private Integer totalSellingPrice;

    private Integer discount;

    private OrderStatus orderStatus;

    private int totalItems;

    @Column(name = "payment_status_detail")
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;

    private LocalDateTime orderDate=LocalDateTime.now();

    private LocalDateTime deliveryDate=LocalDateTime.now().plusDays(7);

}