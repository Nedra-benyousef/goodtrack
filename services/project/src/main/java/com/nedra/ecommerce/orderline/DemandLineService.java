package com.nedra.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandLineService {

    private final DemandLineRepository repository;
    private final DemandLineMapper mapper;

   /* public Integer saveOrderLine(DemandLineRequest request) {
        var order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }*/
   public void saveOrderLine(DemandLineRequest request) {
       DemandLine demandLine = mapper.toOrderLine(request);
       repository.save(demandLine);  // Ensure save works as expected
   }
    public List<DemandLineResponse> findAllByDemandeId(Integer demandeId) {
        return repository.findAllByDemandeId(demandeId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
