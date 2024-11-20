package com.nedra.ecommerce.payment;

import com.nedra.ecommerce.customer.CustomerResponse;
import com.nedra.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {
}
