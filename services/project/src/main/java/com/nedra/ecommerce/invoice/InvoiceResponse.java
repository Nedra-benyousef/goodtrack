package com.nedra.ecommerce.invoice;



import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private Integer id;
    private Integer number;
    private String companyName;
    private String address;
    private Integer codePostal;
    private BigDecimal discount;
    private BigDecimal shippingCharge;
    private String website;
    private BigDecimal totalAmount;
    private String matricule;
    private BigInteger taxNumber;
    private Double tva;
    private String email;
    private BigInteger phone;
    private LocalDateTime createdDate;

    private String demandeReference;
    private BigDecimal demandeTotalAmount;
    private String paymentMethod;
    private List<DemandLineResponse> demandLines;

  @Getter
  @Setter
  @Data// Getters and setters
  @AllArgsConstructor
  @NoArgsConstructor

    public static class DemandLineResponse {
        private Integer ticketId;
        private double quantity;
        private String name;
        private String description;
        private BigDecimal totalAmount;

        // Getters and setters
    }
}
