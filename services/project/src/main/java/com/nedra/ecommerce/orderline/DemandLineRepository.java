package com.nedra.ecommerce.orderline;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandLineRepository extends JpaRepository<DemandLine, Integer> {

    List<DemandLine> findAllByDemandeId(Integer demandeId);

}
