package com.shoponline.ecommerce.payment;



import com.shoponline.ecommerce.customer.CustomerResponse;
import com.shoponline.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
