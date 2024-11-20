package com.nedra.ecommerce.invoice;

import lombok.Data;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class InvoiceRequest {
    private Integer number;
    private String companyName;
    private String address;
    private Integer codePostal;
    private BigDecimal discount;
    private BigDecimal shippingCharge;
    private String website;
    private String matricule;
    private BigInteger taxNumber;
    private Double tva;
    private String email;
    private BigInteger phone;
}
