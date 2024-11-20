package com.nedra.ecommerce.request;

import com.nedra.ecommerce.customer.CustomerResponse;
import com.nedra.ecommerce.customer.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
        name = "ticket-service",
        url = "${application.config.ticket-url}"
        //configuration = FeignClientConfig.class
)


public interface TicketClient {

    /*@GetMapping("/{id}")
    List<PurchaseResponse> purchaseTickets(List<PurchaseRequest> requestBody, @RequestHeader("Authorization") String token);
*/ @GetMapping("/{id}")
    CustomerResponse  findTicketById(@PathVariable("id") String ticketId, @RequestHeader("Authorization") String token);




    @PostMapping("/purchase")
    List<PurchaseResponse> purchaseTickets(List<PurchaseRequest> requestBody ,
                                           @RequestHeader("Authorization") String token);



   /* @GetMapping("/{id}")
    List<PurchaseResponse> purchaseTickets(@PathVariable("id") Integer ticketId, @RequestHeader("Authorization") String token);
*/

}

