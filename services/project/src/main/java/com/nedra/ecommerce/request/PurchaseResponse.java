package com.nedra.ecommerce.request;

import java.math.BigDecimal;

public record PurchaseResponse(
        Integer ticketId,
        double quantity,
        String name,
        String description,
                BigDecimal price
        //,
        //String statut
) {
}
