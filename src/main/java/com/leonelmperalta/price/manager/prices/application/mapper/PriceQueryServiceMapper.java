package com.leonelmperalta.price.manager.prices.application.mapper;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class PriceQueryServiceMapper {

    public LocalDateTime toLocalDateTime(String date) throws InternalServerErrorException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new InternalServerErrorException();
        }
    }

}
