package com.nedra.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String demandReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
