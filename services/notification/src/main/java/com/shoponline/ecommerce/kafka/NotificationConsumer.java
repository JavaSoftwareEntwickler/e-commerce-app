package com.shoponline.ecommerce.kafka;

import com.shoponline.ecommerce.email.EmailService;
import com.shoponline.ecommerce.kafka.order.OrderConfirmation;
import com.shoponline.ecommerce.kafka.payment.PaymentConfirmation;
import com.shoponline.ecommerce.notification.Notification;
import com.shoponline.ecommerce.notification.NotificationRepository;
import com.shoponline.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.shoponline.ecommerce.kafka.TopicType.ORDER_TOPIC;
import static com.shoponline.ecommerce.kafka.TopicType.PAYMENT_TOPIC;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = PAYMENT_TOPIC)
    private void consumePaymentSuccessNotification(PaymentConfirmation confirmation) throws MessagingException {
        log.info(format("consumePaymentSuccessNotification from " + PAYMENT_TOPIC + " :: %s", confirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFERMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(confirmation)
                        .build()
        );

        //send email
        String customerName = confirmation.customerName() + " " + confirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                confirmation.customerEmail(),
                customerName,
                confirmation.totAmount(),
                confirmation.orderReference()
        );
    }
    @KafkaListener(topics = ORDER_TOPIC)
    private void consumeOrderConfirmationNotification(OrderConfirmation confirmation) throws MessagingException {
        log.info(format("consumePaymentSuccessNotification from " + ORDER_TOPIC + " :: %s", confirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFERMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(confirmation)
                        .build()
        );

        //send email
        String customerName = confirmation.customer().firstName() + " " + confirmation.customer().lastName();
        emailService.sendOrderSuccessEmail(
                confirmation.customer().email(),
                customerName,
                confirmation.totAmount(),
                confirmation.orderReference(),
                confirmation.products()

        );
    }
}
