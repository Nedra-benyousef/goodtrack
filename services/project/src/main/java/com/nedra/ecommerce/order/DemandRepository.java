package com.nedra.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemandRepository extends JpaRepository<Demande, Integer> {
    @Override
    Optional<Demande> findById(Integer integer);
}
