package com.nedra.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(
    name = "customer-service",
    url = "${application.config.customer-url}",
        configuration = FeignClientConfig.class
)


public interface CustomerClient {

  @GetMapping("/{id}")
  CustomerResponse  findCustomerById(@PathVariable("id") String customerId, @RequestHeader("Authorization") String token);


}
