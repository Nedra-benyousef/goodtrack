package com.nedra.ecommerce.customer;

import com.nedra.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
/*  @GetMapping("/user")
  public ResponseEntity<UserInfo> getUserInfo(@AuthenticationPrincipal OAuth2AuthenticationToken authentication) {
    String accessToken = ((OAuth2Authentication) authentication.getPrincipal()).getAccessToken().getTokenValue();

    // Call Keycloak UserInfo endpoint
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<UserInfo> response = restTemplate.exchange(
            "http://<keycloak-server>/auth/realms/<realm>/protocol/openid-connect/userinfo",
            HttpMethod.GET,
            entity,
            UserInfo.class
    );

    return ResponseEntity.ok(response.getBody());
  }
}*/
/*  @Autowired
  private Keycloak keycloak; // Assuming you're using Keycloak Spring Boot adapter

  public UserRepresentation getUserById(String userId) {
    return keycloak.realm("your-realm")
            .users()
            .get(userId)
            .toRepresentation();
  }

  public void updateUser(String userId, UpdateUserRequest request) {
    UserResource userResource = keycloak.realm("your-realm").users().get(userId);
    UserRepresentation user = userResource.toRepresentation();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    userResource.update(user);
  }
}


}*/

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public String createCustomer(CustomerRequest request) {
    var customer = this.repository.save(mapper.toCustomer(request));
    return customer.getId();
  }

  /*public void updateCustomer( CustomerRequest request) {
    var customer = this.repository.findById(request.id())
        .orElseThrow(() -> new CustomerNotFoundException(
            String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
        ));
    mergeCustomer(customer, request);
    this.repository.save(customer);
  }*/
  public Customer updateCustomer(String id, CustomerRequest request) {
    Customer customer = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

    // Update fields
    customer.setFirstname(request.firstname());
    customer.setLastname(request.lastname());
    customer.setEmail(request.email());
    customer.setAddress(request.address());


    return repository.save(customer);
  }


  private void mergeCustomer(Customer customer, CustomerRequest request) {
    if (StringUtils.isNotBlank(request.firstname())) {
      customer.setFirstname(request.firstname());
    }
    if (StringUtils.isNotBlank(request.email())) {
      customer.setEmail(request.email());
    }
    if (request.address() != null) {
      customer.setAddress(request.address());
    }
  }

  public List<CustomerResponse> findAllCustomers() {
    return  this.repository.findAll()
            .stream()
            .map(this.mapper::fromCustomer)
            .collect(Collectors.toList());
  }

  public CustomerResponse findById(String id) {
    return this.repository.findById(id)
            .map(mapper::fromCustomer)
            .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
  }

  public boolean existsById(String id) {
    return this.repository.findById(id)
        .isPresent();
  }

  public void deleteCustomer(String id) {
    this.repository.deleteById(id);
  }
}
