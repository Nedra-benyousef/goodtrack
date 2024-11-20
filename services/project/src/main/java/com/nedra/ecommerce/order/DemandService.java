package com.nedra.ecommerce.order;

import com.nedra.ecommerce.customer.CustomerResponse;
import com.nedra.ecommerce.exception.BusinessException;
import com.nedra.ecommerce.kafka.DemandConfirmation;
import com.nedra.ecommerce.customer.CustomerClient;
import com.nedra.ecommerce.kafka.DemandProducer;
import com.nedra.ecommerce.orderline.DemandLine;
import com.nedra.ecommerce.orderline.DemandLineRequest;
import com.nedra.ecommerce.orderline.DemandLineResponse;
import com.nedra.ecommerce.orderline.DemandLineService;
import com.nedra.ecommerce.payment.PaymentClient;
import com.nedra.ecommerce.payment.PaymentRequest;
import com.nedra.ecommerce.request.PurchaseResponse;
import com.nedra.ecommerce.request.TicketClient;
import com.nedra.ecommerce.request.TicketClientt;
import com.nedra.ecommerce.request.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.security.oauth2.jwt.Jwt;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepository repository;
    private final DemandMapper mapper;

    @Autowired
    private CustomerClient customerClient;
    //private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final TicketClient ticketClient;
    private final DemandLineService orderLineService;
    private final DemandProducer demandProducer;
    //private final AuditorAware<String> auditorAware;
    /*private String getCustomerIdFromToken() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getSubject();  // Retrieve customer ID from the token's 'sub' claim
    }*/
    /*private String getCustomerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return authentication.getPrincipal().toString();
        } else {
            throw new IllegalStateException("Authentication details not found");
        }
    }*/
    private String getCustomerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Authentication is not available.");
        }

        // Ensure the authentication is an instance of JwtAuthenticationToken
        if (!(authentication instanceof JwtAuthenticationToken)) {
            throw new IllegalStateException("Authentication is not a JWT.");
        }

        // Cast to JwtAuthenticationToken
        JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthentication.getToken();

        // Retrieve the customer ID from the token's 'sub' claim
        return jwt.getSubject();
    }

   /* public Integer createOrder(
            DemandRequest request
    ) {
        //CustomerResponse customer = this.customerClient.findCustomerById(request.customerId());

        var order = this.repository.save(mapper.toOrder(request));        // mapper.toTicket(request);
       /* var purchasedProducts = ticketClient.purchaseTickets(request.tickets());


        for (PurchaseRequest purchaseRequest : request.tickets()) {
            orderLineService.saveOrderLine(
                    new DemandLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.ticketId(),
                            purchaseRequest.quantity()
                    )
            );
        }*/
       /* var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference()

        );
        paymentClient.requestOrderPayment(paymentRequest);

        demandProducer.sendOrderConfirmation(
                new DemandConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod()
                        //,
                        //customer,


                        //purchasedProducts
                )
        );
        return order.getId();
    }*/
    @Transactional
    public Integer createOrder(DemandRequest request, String token) {
        var customer = this.customerClient.findCustomerById(request.customerId(), token);
               // .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));
        //CustomerResponse customer = this.customerClient.findCustomerById(request.customerId());
      //  String customerId = getCustomerIdFromToken(); // Extract the customer ID from the token
       // Flogger.info("Injecting Authorization token: Bearer {}", token);

       // CustomerResponse customer = this.customerClient.findCustomerById(customerId);
              //  .orElseThrow(() -> new BusinessException("Cannot create order: No customer exists with the provided ID"));
        System.out.println(token);
         //  ticketClient.purchaseTickets(request.tickets(), token);
        var purchasedTickets = ticketClient.purchaseTickets(request.tickets(),token);

        System.out.println(purchasedTickets);
        for (PurchaseResponse ticket : purchasedTickets) {
            System.out.println("Ticket ID: " + ticket.ticketId());
            System.out.println("Ticket Name: " + ticket.name());  // Ensure it's not null
            System.out.println("Ticket Description: " + ticket.description());  // Ensure it's not null
        }
        var order = this.repository.save(mapper.toOrder(request));
        for (PurchaseResponse ticket : purchasedTickets) {
            System.out.println("Creating Order Line for Ticket ID: " + ticket.ticketId());

            // Map PurchaseResponse to PurchaseRequest to use in DemandLineRequest
            PurchaseRequest purchaseRequest = new PurchaseRequest(
                    ticket.ticketId(),
                    ticket.quantity(),
                    ticket.name(),
                    ticket.description()
            );

            // Print to ensure the values are not null
            System.out.println("Mapped Ticket Name: " + purchaseRequest.name());
            System.out.println("Mapped Ticket Description: " + purchaseRequest.description());

        //for (PurchaseRequest purchaseRequest : request.tickets()) {
            orderLineService.saveOrderLine(
                    new DemandLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.ticketId(),
                            purchaseRequest.quantity(),
                            purchaseRequest.name(),
                            purchaseRequest.description()
                    )
            );

        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest, token);

        demandProducer.sendOrderConfirmation(
                new DemandConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,

                        purchasedTickets
                )
        );

        return order.getId();
    }
    @Transactional
    public void deleteOrder(Integer orderId) {
        var order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        repository.delete(order);
    }
    @Transactional
    public Demande updateOrder(Integer orderId, DemandRequest request) {
        // Fetch the existing order
        var existingOrder = repository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found with ID: " + orderId));

        // Update order fields
        existingOrder.setReference(request.reference());
        existingOrder.setPaymentMethod(request.paymentMethod());
        existingOrder.setCustomerId(request.customerId());

        // Clear existing demand lines if fully replacing the list
        if (existingOrder.getDemandLines() != null) {
            existingOrder.getDemandLines().clear();
        } else {
            existingOrder.setDemandLines(new ArrayList<>());
        }

        // Add new demand lines from the request
        for (PurchaseRequest purchaseRequest : request.tickets()) {
            DemandLine demandLine = new DemandLine();
            demandLine.setTicketId(purchaseRequest.ticketId());
            demandLine.setQuantity(purchaseRequest.quantity());
            demandLine.setName(purchaseRequest.name());
            demandLine.setDescription(purchaseRequest.description());
            demandLine.setDemande(existingOrder);  // Set the relationship

            // Add demand line to the order
            existingOrder.getDemandLines().add(demandLine);
        }

        // Save and return the updated order
        return repository.save(existingOrder);
    }



   /* @Transactional

    public Integer createOrder(DemandRequest request) {
       /* var customer = this.customerClient.findCustomerById(request.customerId())

                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));*/
       /* String customerId = getCustomerIdFromToken();
        var customer = this.customerClient.findCustomerById(customerId)
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));


        var order = this.repository.save(mapper.toOrder(request));
        var purchasedTickets = ticketClient.purchaseTickets(request.tickets());

        for (PurchaseRequest purchaseRequest : request.tickets()) {
            orderLineService.saveOrderLine(
                    new DemandLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.ticketId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        demandProducer.sendOrderConfirmation(
                new DemandConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedTickets
                )
        );

        return order.getId();
    }*/

    public List<DemandResponse> findAllOrders() {

        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    /*public DemandConfirmation getOrderDetails(Integer orderId, String token) {
        var order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Retrieve customer details
        CustomerResponse customer = customerClient.findCustomerById(order.getCustomerId(), token);

        // Retrieve ticket details
        List<PurchaseResponse> tickets = ticketClient.purchaseTickets(List<PurchaseResponse>,token);

        // Map order data to DemandConfirmation
        return new DemandConfirmation(
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                customer,
                tickets
        );
    }*/
   /*public List<DemandResponse> findAllOrders() {
       return this.repository.findAll()
               .stream()
               .map(order -> {
                   // Fetch customer details
                   CustomerResponse customer = customerClient.findCustomerById(order.getCustomerId());

                   // Fetch tickets associated with the order
                   List<PurchaseResponse> tickets = order.getDemandLines().stream()
                           .map(demandLine -> ticketClient.purchaseTickets(demandLine.getTicketId())
                                   )
                           .collect(Collectors.toList());

                   // Map order and additional details to DemandResponse
                   return new DemandResponse(
                           order.getId(),
                           order.getReference(),
                           order.getTotalAmount(),
                           order.getPaymentMethod(),
                           order.getCustomerId(),
                           order.getCreatedDate(),
                           order.getLastModifiedDate(),
                           customer,
                           tickets
                   );
               })
               .collect(Collectors.toList());
   }*/

    public DemandResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
    /*@Transactional(readOnly = true)
    public Demande findOrderById(Integer orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }*/
    public DemandResponse findOrderById(Integer orderId) {
        Demande demande = repository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return mapper.fromOrder(demande);
    }
   /* public byte[] generateInvoice(DemandResponse order, CustomerResponse customer, PaymentRequest payment) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Invoice for Order ID: " + order.id()));
        document.add(new Paragraph("Customer: " + customer.firstname()));
        document.add(new Paragraph("Order Amount: " + order.amount()));
        document.add(new Paragraph("Payment Method: " + payment.paymentMethod()));
        //document.add(new Paragraph("Payment Status: " + payment.getStatus()));

        // Add more details as needed, e.g., purchased tickets, etc.
        document.close();

        return outputStream.toByteArray();
    }*/
}


