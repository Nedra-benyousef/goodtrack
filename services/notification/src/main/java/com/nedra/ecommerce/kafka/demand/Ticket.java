package com.nedra.ecommerce.kafka.demand;

import java.math.BigDecimal;

public record  Ticket(
        Integer ticketId,
        String name,
        String description,
        BigDecimal price,
        double quantity

) {
}
