package com.ecommerce.shopping_cart.model;

import com.ecommerce.shopping_cart.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {

    private String paymentId;
    private String razorPaymentLinkId;
    private String razorPaymentLinkReferenceId;
    private String razorPaymentLinkStatus;
    private String razorPaymentLinkPaymentID;
    private PaymentStatus paymentStatus;
}
