package com.shoponline.ecommerce.kafka;

import com.shoponline.ecommerce.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static com.shoponline.ecommerce.kafka.Topic.ORDER_TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;
    public void sendOrderConfirmation(OrderConfirmation order){
        log.info("Sending order confirmation");
        Message<OrderConfirmation> msg = MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC)
                .build();
        kafkaTemplate.send(msg);
    }
}
