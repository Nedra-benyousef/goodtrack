package com.nedra.ecommerce.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PurchaseRequest(
        @NotNull(message = "ticket is mandatory")
        Integer ticketId,
        //@Positive(message = "Quantity is mandatory")
        double quantity,
        String name,
        String description

) {
}
