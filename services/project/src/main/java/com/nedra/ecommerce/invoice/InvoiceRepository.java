package com.nedra.ecommerce.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    // Vous pouvez ajouter ici des méthodes personnalisées si nécessaire, par exemple :
    // Optional<Invoice> findByNumber(Integer number);
}