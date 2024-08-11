package com.shoponline.ecommerce.kafka;


import com.shoponline.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;


public record PaymentConfirmation(
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        String customerName,
        String customerLastName,
        String customerEmail

) {
}
