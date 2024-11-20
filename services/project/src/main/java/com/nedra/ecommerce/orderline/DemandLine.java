package com.nedra.ecommerce.orderline;


import com.nedra.ecommerce.order.Demande;
import com.nedra.ecommerce.request.PurchaseRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customer_line")
public class DemandLine {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer ticketId;
    private double quantity;
    private String name;
    private String description;
    private BigDecimal TotalAmount;
    @ManyToOne
    @JoinColumn(name = "demande_id")
    private Demande demande;



    /*public DemandLine(Demande demande, PurchaseRequest ticket) {
        this.demande=demande;
        this.ticketId= ticket.ticketId();
        this.name= ticket.name();
        this.description=ticket.description();
    }*/
}
