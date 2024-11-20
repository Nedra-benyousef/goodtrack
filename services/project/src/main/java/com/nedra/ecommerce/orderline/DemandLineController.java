package com.nedra.ecommerce.orderline;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class DemandLineController {

    private final DemandLineService service;

    @GetMapping("/order/{demande_id}")
    public ResponseEntity<List<DemandLineResponse>> findByOrderId(
            @PathVariable("demande_id") Integer demandeId
    ) {
        return ResponseEntity.ok(service.findAllByDemandeId(demandeId));
    }
}
