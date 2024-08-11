package com.shoponline.ecommerce.email;

import com.shoponline.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shoponline.ecommerce.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.shoponline.ecommerce.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String toEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("miaemail@gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> map = new HashMap<>();
        map.put("customerName", customerName);
        map.put("amount", amount);
        map.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(map);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getTemplate());

        String htmltemplate = "";
        try{

            htmltemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmltemplate,true);
            messageHelper.setTo(toEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully sent to %s with template : %s", toEmail, templateName));

        } catch (MessagingException e) {
            log.warn("Cannot send email to {}", toEmail);
        }
    }

    @Async
    public void sendOrderSuccessEmail(String toEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("miaemail@gmail.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> var = new HashMap<>();
        var.put("customerName", customerName);
        var.put("totalAmount", amount);
        var.put("orderReference", orderReference);
        var.put("products", products);

        Context context = new Context();
        context.setVariables(var);
        messageHelper.setSubject(ORDER_CONFIRMATION.getTemplate());

        String htmltemplate = "";
        try{

            htmltemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmltemplate,true);
            messageHelper.setTo(toEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email successfully sent to %s with template : %s", toEmail, templateName));

        } catch (MessagingException e) {
            log.warn("Cannot send email to {}", toEmail);
        }
    }
}
