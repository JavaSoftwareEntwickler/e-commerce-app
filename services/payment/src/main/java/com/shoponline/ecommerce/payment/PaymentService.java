package com.shoponline.ecommerce.payment;

import com.shoponline.ecommerce.kafka.PaymentConfirmation;
import com.shoponline.ecommerce.kafka.PaymentNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final MapperRepository mapper;
    private final PaymentNotificationProducer paymentNotification;

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));

        //Send payment notification
        paymentNotification.sendPaymentConfirmation(
                new PaymentConfirmation(
                    request.orderReference(),
                    request.amount(),
                    request.paymentMethod(),
                    request.customer().firstName(),
                    request.customer().lastName(),
                    request.customer().email()
                )
        );
        return payment.getId();
    }
}
