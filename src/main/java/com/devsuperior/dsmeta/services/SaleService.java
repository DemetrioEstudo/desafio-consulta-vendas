package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

    @Transactional(readOnly = true)
    public List<Sale> getReport(String name, String minDate, String maxDate) {
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

        return repository.findSalesBySellerNameAndDateBetween(
                finalName,
                finalMinDate,
                finalMaxDate
        );
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





