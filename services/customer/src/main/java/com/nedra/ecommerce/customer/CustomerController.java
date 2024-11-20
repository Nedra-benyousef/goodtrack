package com.nedra.ecommerce.customer;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService service;

  @PostMapping
  public ResponseEntity<String> createCustomer(
      @RequestBody @Valid CustomerRequest request
  ) {
    return ResponseEntity.ok(this.service.createCustomer(request));
  }

  /*@PutMapping
  public ResponseEntity<Void> updateCustomer(
      @RequestBody @Valid CustomerRequest request
  ) {
    this.service.updateCustomer(request);
    return ResponseEntity.accepted().build();
  }*/
  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody CustomerRequest request) {
    Customer updatedCustomer = service.updateCustomer(id, request);
    return ResponseEntity.ok(updatedCustomer);
  }
  @GetMapping
  public ResponseEntity<List<CustomerResponse>> findAll() {
    return ResponseEntity.ok(this.service.findAllCustomers());
  }

  @GetMapping("/exists/{customer-id}")
  public ResponseEntity<Boolean> existsById(
      @PathVariable("customer-id") String customerId
  ) {
    return ResponseEntity.ok(this.service.existsById(customerId));
  }

  @GetMapping("/{customer-id}")
  public ResponseEntity<CustomerResponse> findById(
      @PathVariable("customer-id") String customerId
  ) {
    return ResponseEntity.ok(this.service.findById(customerId));
  }

  @DeleteMapping("/{customer-id}")
  public ResponseEntity<String> delete(
      @PathVariable("customer-id") String customerId
  ) {
    //this.service.deleteCustomer(customerId);
    //return ResponseEntity.accepted().build();
    try {
      // Call the service method to delete the ticket
      service.deleteCustomer(customerId);

      // Return a success response
      return ResponseEntity.ok("Customer deleted successfully.");
    } catch (Exception e) {
      // Handle any exceptions and return a 500 Internal Server Error response
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error deleting the customer: " + e.getMessage());
    }
  }

}
