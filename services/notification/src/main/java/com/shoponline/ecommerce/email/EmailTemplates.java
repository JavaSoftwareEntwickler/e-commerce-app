package com.shoponline.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order successfully processed");

    @Getter
    private final String template;
    private final String emailSubject;

    EmailTemplates(String template, String emailSubject) {
        this.template = template;
        this.emailSubject = emailSubject;
    }
}
