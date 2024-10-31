package com.ecommerce.shopping_cart.model;

import com.ecommerce.shopping_cart.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String email;
    // password is not stored in the database
    // it is only used for authentication
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String mobile;

    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;


    @OneToMany
    private Set<Address> addressSet=new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<Coupon> usedCoupons=new HashSet<>();
}
