package com.nedra.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nedra.ecommerce.customer.CustomerResponse;
import com.nedra.ecommerce.request.PurchaseResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public record DemandResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId,
        //,
       // LocalDateTime createdDate,
        //LocalDateTime lastModifiedDate,

        //CustomerResponse customer,  // Include customer details
        List<PurchaseResponse> tickets  // Include list of ticket details
) {

}
