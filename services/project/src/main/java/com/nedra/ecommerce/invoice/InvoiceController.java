package com.nedra.ecommerce.invoice;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/createinvoice/{demandeId}")
    public ResponseEntity<Invoice> createInvoice(
            @PathVariable Integer demandeId,
            @RequestBody InvoiceRequest invoiceRequest) {
        Invoice createdInvoice = invoiceService.createInvoice(demandeId, invoiceRequest);
        return ResponseEntity.ok(createdInvoice);
    }
    /*@GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }*/
    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Integer id) {
        InvoiceResponse invoiceResponse = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoiceResponse);
    }
}

