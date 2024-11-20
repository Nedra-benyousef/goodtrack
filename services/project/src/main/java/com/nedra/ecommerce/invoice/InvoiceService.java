package com.nedra.ecommerce.invoice;

import com.nedra.ecommerce.order.DemandRepository;
import com.nedra.ecommerce.order.Demande;
import com.nedra.ecommerce.orderline.DemandLine;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DemandRepository demandeRepository;  // Repository to fetch Demande dataF

    @Transactional
    public Invoice createInvoice(Integer demandeId, InvoiceRequest invoiceRequest) {
        // Retrieve order/demande data only once
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + demandeId));

        // Calculate total with discount and shipping, handling nulls with default values
        BigDecimal totalAmount = (demande.getTotalAmount() != null ? demande.getTotalAmount() : BigDecimal.ZERO)
                .subtract(invoiceRequest.getDiscount() != null ? invoiceRequest.getDiscount() : BigDecimal.ZERO)
                .add(invoiceRequest.getShippingCharge() != null ? invoiceRequest.getShippingCharge() : BigDecimal.ZERO);

        // Build and save the invoice object in one step
        Invoice invoice = invoiceRepository.save(Invoice.builder()
                .number(invoiceRequest.getNumber())
                .demande(demande)
                .companyName(invoiceRequest.getCompanyName())
                .address(invoiceRequest.getAddress())
                .codePostal(invoiceRequest.getCodePostal())
                .discount(invoiceRequest.getDiscount() != null ? invoiceRequest.getDiscount() : BigDecimal.ZERO)
                .shippingCharge(invoiceRequest.getShippingCharge() != null ? invoiceRequest.getShippingCharge() : BigDecimal.ZERO)
                .website(invoiceRequest.getWebsite())
                .totalAmount(totalAmount)
                .matricule(invoiceRequest.getMatricule())
                .taxNumber(invoiceRequest.getTaxNumber())
                .tva(invoiceRequest.getTva())
                .email(invoiceRequest.getEmail())
                .phone(invoiceRequest.getPhone())
                .createdDate(LocalDateTime.now())
                .build());

        return invoice;
    }

    /*public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }*/

    public List<InvoiceResponse> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(this::mapToInvoiceResponse).collect(Collectors.toList());
    }

    private InvoiceResponse mapToInvoiceResponse(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();

        response.setId(invoice.getId());
        response.setNumber(invoice.getNumber());
        response.setCompanyName(invoice.getCompanyName());
        response.setAddress(invoice.getAddress());
        response.setCodePostal(invoice.getCodePostal());
        response.setDiscount(invoice.getDiscount());
        response.setShippingCharge(invoice.getShippingCharge());
        response.setWebsite(invoice.getWebsite());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setMatricule(invoice.getMatricule());
        response.setTaxNumber(invoice.getTaxNumber());
        response.setTva(invoice.getTva());
        response.setEmail(invoice.getEmail());
        response.setPhone(invoice.getPhone());
        response.setCreatedDate(invoice.getCreatedDate());

        if (invoice.getDemande() != null) {
            response.setDemandeReference(invoice.getDemande().getReference());
            response.setDemandeTotalAmount(invoice.getDemande().getTotalAmount());
            response.setPaymentMethod(invoice.getDemande().getPaymentMethod().name());

            List<InvoiceResponse.DemandLineResponse> demandLineResponses = invoice.getDemande().getDemandLines()
                    .stream()
                    .map(line -> {
                        InvoiceResponse.DemandLineResponse lineResponse = new InvoiceResponse.DemandLineResponse();
                        lineResponse.setTicketId(line.getTicketId());
                        lineResponse.setQuantity(line.getQuantity());
                        lineResponse.setName(line.getName());
                        lineResponse.setDescription(line.getDescription());
                        lineResponse.setTotalAmount(line.getTotalAmount());
                        return lineResponse;
                    })
                    .collect(Collectors.toList());

            response.setDemandLines(demandLineResponses);
        }

        return response;
    }

    public InvoiceResponse getInvoiceDetails(Integer invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

       // Demande demande = invoice.getDemande(); // Get the associated Demande
       // List<DemandLine> demandLines = demande.getDemandLines(); // Get DemandLine items
        List<InvoiceResponse.DemandLineResponse> demandLineResponses = invoice.getDemande().getDemandLines()
                .stream()
                .map(line -> new InvoiceResponse.DemandLineResponse(
                        line.getTicketId(),
                        line.getQuantity(),
                        line.getName(),
                        line.getDescription(),
                        line.getTotalAmount()
                ))
                .collect(Collectors.toList());

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getNumber(),
                invoice.getCompanyName(),
                invoice.getAddress(),
                invoice.getCodePostal(),
                invoice.getDiscount(),
                invoice.getShippingCharge(),
                invoice.getWebsite(),
                invoice.getTotalAmount(),
                invoice.getMatricule(),
                invoice.getTaxNumber(),
                invoice.getTva(),
                invoice.getEmail(),
                invoice.getPhone(),
                invoice.getCreatedDate(),
               // demande.getCustomerId(),
                invoice.getDemande().getReference(),
                invoice.getDemande().getTotalAmount(),
                invoice.getDemande().getPaymentMethod().name(),
                demandLineResponses
        );
    }

    public InvoiceResponse getInvoiceById(Integer id) {
        // Fetch the invoice with associated Demande details
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found with ID: " + id));

        // Map to InvoiceResponse with detailed Demande information
        return mapToInvoiceResponse(invoice);
    }
}