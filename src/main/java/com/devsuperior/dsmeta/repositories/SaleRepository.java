package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id,obj.amount,obj.date,obj.seller.name) " +
            "from Sale obj" +
            " where obj.date between :start and :finish and UPPER(obj.seller.name) like UPPER(concat('%',:name,'%'))")
    Page<SaleMinDTO> searchByDatesAndName(LocalDate start, LocalDate finish, String name, Pageable pageable);
    @Query("select new com.devsuperior.dsmeta.dto.SaleSummaryDTO(sal.seller.name,sum(sal.amount)) " +
            "from Sale sal " +
            "where sal.date between :start and :finish group by sal.seller.id")
    List<SaleSummaryDTO> searchSummary(LocalDate start, LocalDate finish);
}
