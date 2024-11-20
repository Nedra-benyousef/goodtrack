package com.nedra.ecommerce.kafka.demand;

import com.nedra.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record DemandConfirmation(
        String demandReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Ticket> tickets

) {
}
