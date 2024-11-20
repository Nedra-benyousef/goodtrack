package com.nedra.ecommerce.orderline;

public record DemandLineResponse(
        Integer id,
        double quantity,
        String name,
        String description

) { }
