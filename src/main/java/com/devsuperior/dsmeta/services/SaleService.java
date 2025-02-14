package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleMinDTO> getReport(String initialDate, String finalDate, String name, Pageable pageable) {
        LocalDate maxDate = finalDate == null || finalDate.isEmpty() ?
                LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) :
                LocalDate.parse(finalDate);
        LocalDate minDate = initialDate == null || initialDate.isEmpty() ?
                maxDate.minusYears(1L):
                LocalDate.parse(initialDate);
        return repository.searchByDatesAndName(minDate, maxDate, name, pageable);
    }

    public List<SaleSummaryDTO> getSummary(String initialDate, String finalDate){
        LocalDate maxDate = finalDate == null || finalDate.isEmpty() ?
                LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) :
                LocalDate.parse(finalDate);
        LocalDate minDate = initialDate == null || initialDate.isEmpty() ?
                maxDate.minusYears(1L):
                LocalDate.parse(initialDate);
        return repository.searchSummary(minDate,maxDate);
    }
}
