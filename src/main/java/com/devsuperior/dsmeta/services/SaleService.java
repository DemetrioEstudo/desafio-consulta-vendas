package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;


@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = saleRepository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }


    @Transactional(readOnly = true)
    public Page<SaleReportDTO> getReport(String name, String minDate, String maxDate, Pageable pageable) {
        // Trata o nome
        String finalName = (name != null && !name.trim().isEmpty()) ? name.trim() : "";

        // Converte e trata maxDate (data final)
        LocalDate finalMaxDate = parseDate(maxDate);
        if (finalMaxDate == null) {
            finalMaxDate = LocalDate.now();
        }

        // Converte e trata minDate (data inicial)
        LocalDate finalMinDate = parseDate(minDate);
        if (finalMinDate == null) {
            finalMinDate = finalMaxDate.minusYears(1);
        }

        return saleRepository.findSalesReport(finalName, finalMinDate, finalMaxDate, pageable);
    }


    @Transactional(readOnly = true)
    public Page<SaleSummaryDTO> getSummary(String minDate, String maxDate, Pageable pageable) {
        LocalDate finalMaxDate = parseDate(maxDate);
        if (finalMaxDate == null) {
            finalMaxDate = LocalDate.now();
        }

        LocalDate finalMinDate = parseDate(minDate);
        if (finalMinDate == null) {
            finalMinDate = finalMaxDate.minusYears(1);
        }

        return saleRepository.findSalesSummary(finalMinDate, finalMaxDate,pageable);
    }







    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}