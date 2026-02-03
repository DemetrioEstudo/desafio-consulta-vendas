package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(" +
            "s.id, s.date, s.amount, sel.name) " +
            "FROM Sale s JOIN s.seller sel WHERE " +
            "LOWER(sel.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND s.date BETWEEN :minDate AND :maxDate " +
            "ORDER BY s.date DESC")
    Page<SaleReportDTO> findSalesReport(
            @Param("name") String name,
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            Pageable pageable
    );

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(" +
            "sel.name, SUM(s.amount)) " +
            "FROM Sale s JOIN s.seller sel WHERE " +
            "s.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY sel.name " +
            "ORDER BY sel.name")
    Page<SaleSummaryDTO> findSalesSummary(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate, Pageable pageable
    );



}
