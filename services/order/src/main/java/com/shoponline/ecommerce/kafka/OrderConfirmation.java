package com.shoponline.ecommerce.kafka;

import com.shoponline.ecommerce.customer.CustomerResponse;
import com.shoponline.ecommerce.order.PaymentMethod;
import com.shoponline.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
