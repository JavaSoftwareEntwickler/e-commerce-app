package com.shoponline.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.shoponline.ecommerce.kafka.Topic.PAYMENT_TOPIC;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentNotificationProducer {

    private final KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;
    public void sendPaymentConfirmation(PaymentConfirmation request){
        log.info(String.format("Sending payment confirmation with request body: %s", request));
        Message<PaymentConfirmation> msg = MessageBuilder
                .withPayload(request)
                .setHeader(TOPIC, PAYMENT_TOPIC)
                .build();
        kafkaTemplate.send(msg);
    }
}
