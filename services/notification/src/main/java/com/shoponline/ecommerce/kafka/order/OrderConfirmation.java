package com.shoponline.ecommerce.kafka.order;

import com.shoponline.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products

) {
}
