package com.nedra.ecommerce.kafka.demand;

public record Customer(
    String id,
    String firstname,
    String lastname,
    String email
) {

}
