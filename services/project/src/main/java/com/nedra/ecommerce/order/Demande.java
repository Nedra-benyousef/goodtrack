package com.nedra.ecommerce.order;

import com.nedra.ecommerce.orderline.DemandLine;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Table(name = "customer_demande")
public class Demande {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(unique = true,  nullable = false)
  private String reference;

  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  private String customerId;
  @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)

  //@OneToMany(mappedBy = "demande")
  private List<DemandLine> demandLines;

  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(insertable = false)
  private LocalDateTime lastModifiedDate;
}
