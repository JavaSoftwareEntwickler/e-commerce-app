package com.shoponline.ecommerce.kafka.payment;

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
