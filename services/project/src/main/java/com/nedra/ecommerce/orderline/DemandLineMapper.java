package com.nedra.ecommerce.orderline;

import com.nedra.ecommerce.order.Demande;
import org.springframework.stereotype.Service;

@Service
public class DemandLineMapper {
    public DemandLine toOrderLine(DemandLineRequest request) {
        return DemandLine.builder()
                .id(request.orderId())
                .ticketId(request.ticketId())
                .quantity(request.quantity())
                .name(request.name())
                .description(request.description())
                .demande(
                        Demande.builder()
                                .id(request.orderId())
                                .build()
                )

                .build();
    }

    public DemandLineResponse toOrderLineResponse(DemandLine demandLine) {
        return new DemandLineResponse(
                demandLine.getId(),
                demandLine.getQuantity(),
                demandLine.getName(),
                demandLine.getDescription()
        );
    }
}
