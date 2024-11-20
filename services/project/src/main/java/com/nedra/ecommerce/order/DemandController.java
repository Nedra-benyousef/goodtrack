package com.nedra.ecommerce.order;

import java.util.List;

import com.nedra.ecommerce.kafka.DemandConfirmation;
import com.nedra.ecommerce.request.PurchaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



  /*public String getCustomerIdFromToken() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return jwt.getSubject();  // 'sub' claim as customer ID (from Keycloak)
  }*/
  /*public Mono<String> getCustomerIdFromToken() {
    return ReactiveSecurityContextHolder.getContext()
            .map(securityContext -> {
              Jwt jwt = (Jwt) securityContext.getAuthentication().getPrincipal();
              return jwt.getSubject();  // Retrieve 'sub' claim as the customer ID
            });
  }*/
  /*public String getCustomerIdFromToken() {
    JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new IllegalStateException("Authentication details not found");
    }
    return authentication.getToken().getClaim("preferred_username"); // or another claim that identifies the user
  }*/
  @RestController
  @RequestMapping("/api/v1/orders")
  @RequiredArgsConstructor
  public class DemandController {

    private final DemandService service;
    private final DemandMapper demandMapper;
    private final DemandRepository demandRepository;

  @PostMapping("/test")
  public String test() {
    return "API is workingg";
  }

  @PostMapping
  public ResponseEntity<Integer> createOrder(
          @RequestBody @Valid DemandRequest request, @RequestHeader("Authorization") String token
  ) {
    return ResponseEntity.ok(this.service.createOrder(request, token));
  }

  @GetMapping
  public ResponseEntity<List<DemandResponse>> findAll() {
    return ResponseEntity.ok(this.service.findAllOrders());
  }

  /*@GetMapping("/{demande-id}")
  public ResponseEntity<DemandResponse> findById(
      @PathVariable("demande-id") Integer orderId
  ) {
    return ResponseEntity.ok(this.service.findById(orderId));
  }
*/
    /*@GetMapping("/{orderId}")
    public ResponseEntity<DemandResponse> findOrderById(@PathVariable("orderId") Integer orderId) {
      // Retrieve the specific order from the service layer
      Demande order = service.findOrderById(orderId);

      // Map the order to a DemandResponse, which includes ticket details
      DemandResponse orderResponse = demandMapper.fromOrder(order);

      return ResponseEntity.ok(orderResponse);
    }*/
  @GetMapping("/{demande-id}")
  public ResponseEntity<DemandResponse> findById(@PathVariable("demande-id") Integer orderId) {
    return ResponseEntity.ok(service.findOrderById(orderId));
  }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
      service.deleteOrder(orderId);
      return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Demande> updateOrder(@PathVariable Integer orderId, @RequestBody DemandRequest request) {
      var updatedOrder = service.updateOrder(orderId, request);
      return ResponseEntity.ok(updatedOrder);
    }

  }
    /*@GetMapping("/details/{orderId}")
    public DemandConfirmation getOrderDetails(
            @PathVariable("orderId") Integer orderId,
            @RequestHeader("Authorization") String token) {
      return service.getOrderDetails(orderId, token);
    }*/
   /* @GetMapping("/tickets/{orderId}")
    public List<PurchaseResponse> getTicketsByOrderId(@PathVariable Integer orderId) {
      return service.findTicketsByOrderId(orderId);
    }
    @GetMapping("/{orderId}/confirmation")
    public ResponseEntity<DemandConfirmation> getOrderConfirmation(@PathVariable Integer orderId) {
      DemandConfirmation confirmation = service.getOrderConfirmationById(orderId);
      return ResponseEntity.ok(confirmation);
    }*/

