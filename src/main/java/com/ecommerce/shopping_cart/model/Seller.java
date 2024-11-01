package com.ecommerce.shopping_cart.model;

import com.ecommerce.shopping_cart.domain.AccountStatus;
import com.ecommerce.shopping_cart.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerName;

    @Column(unique = true,nullable = false)
    private String email;

    private String password;

    private String mobile;

    @Embedded
    private BusinessDetails businessDetails=new BusinessDetails();

    @Embedded
    private BankDetails bankDetails=new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickupAddress=new Address();

    private String GSTIN;

    private USER_ROLE role=USER_ROLE.ROLE_SELLER;

    private boolean isEmailVerified=false;

    private AccountStatus accountStatus= AccountStatus.PENDING_VERIFICATION;


}
