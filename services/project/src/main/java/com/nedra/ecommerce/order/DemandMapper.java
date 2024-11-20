package com.nedra.ecommerce.order;

import com.nedra.ecommerce.request.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandMapper {


  public Demande toOrder(DemandRequest request) {
    /*if (request == null) {
      return null;
    }*/

    return Demande.builder()
        .id(request.id())
        .reference(request.reference())
        .paymentMethod(request.paymentMethod())
        .customerId(request.customerId())


        .build();
  }

  public DemandResponse fromOrder(Demande demande) {
    // Map DemandLine entries to PurchaseResponse (or similar ticket DTO)
    List<PurchaseResponse> ticketDetails = demande.getDemandLines().stream()
            .map(line -> new PurchaseResponse(
                    line.getTicketId(),
                    line.getQuantity(),
                    line.getName(),
                    line.getDescription(),
                    line.getTotalAmount()
                    //,
                    //line.get
            ))
            .collect(Collectors.toList());

    return new DemandResponse(
        demande.getId(),
        demande.getReference(),
        demande.getTotalAmount(),
        demande.getPaymentMethod(),
        demande.getCustomerId(),
            ticketDetails



    );
  }
}
