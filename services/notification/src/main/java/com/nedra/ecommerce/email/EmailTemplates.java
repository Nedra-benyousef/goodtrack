package com.nedra.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    DEMAND_CONFIRMATION("demand-confirmation.html", "Demand confirmation")
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
