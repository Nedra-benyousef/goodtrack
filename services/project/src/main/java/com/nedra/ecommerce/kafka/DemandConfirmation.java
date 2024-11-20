package com.nedra.ecommerce.kafka;

import com.nedra.ecommerce.customer.CustomerResponse;
import com.nedra.ecommerce.order.PaymentMethod;
import com.nedra.ecommerce.request.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record DemandConfirmation(
        String demandReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod
        ,
        CustomerResponse customer,
        List<PurchaseResponse> tickets

) {
}
