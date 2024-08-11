package com.shoponline.ecommerce.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import static com.shoponline.ecommerce.kafka.Topic.PAYMENT_TOPIC;

public class KafkaPaymentTopicConfig {

    @Bean
    public NewTopic paymentTopic(){
        return TopicBuilder
                .name(PAYMENT_TOPIC)
                .build();
    }
}
