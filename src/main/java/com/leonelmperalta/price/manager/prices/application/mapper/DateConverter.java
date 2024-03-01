package com.leonelmperalta.price.manager.prices.application.mapper;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateConverter {

    public LocalDateTime toLocalDateTime(String date) throws InternalServerErrorException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new InternalServerErrorException();
        }
    }

    public String toISO8601String(LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
