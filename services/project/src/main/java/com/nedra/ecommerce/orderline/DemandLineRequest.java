package com.nedra.ecommerce.orderline;

public record DemandLineRequest(
        Integer id,
        Integer orderId,
        Integer ticketId,
        double quantity,
        String name,
        String description
) {
}
