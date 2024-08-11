package com.shoponline.ecommerce.payment;

import org.springframework.stereotype.Service;

@Service
public class MapperRepository {
    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .amout(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
}
