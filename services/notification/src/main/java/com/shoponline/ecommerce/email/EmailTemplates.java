package com.shoponline.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confermation.html", "Payment successflully processed"),
    ORDER_CONFIRMATION("order-confermation.html", "Order successflully processed");

    @Getter
    private final String template;
    private final String emailSubject;

    EmailTemplates(String template, String emailSubject) {
        this.template = template;
        this.emailSubject = emailSubject;
    }
}
