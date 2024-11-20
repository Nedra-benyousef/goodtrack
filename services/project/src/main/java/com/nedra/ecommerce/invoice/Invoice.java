package com.nedra.ecommerce.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedra.ecommerce.order.Demande;
import com.nedra.ecommerce.orderline.DemandLine;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer number;
    @OneToOne
    @JoinColumn(name = "demande_id", nullable = false)
    @JsonIgnore
    private Demande demande;
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
    @CreatedDate
    private LocalDateTime createdDate;
}