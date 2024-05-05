package com.rest.common.util;

import com.rest.common.exception.RestApiInvalidDataException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate parseDate(String dateString) throws RestApiInvalidDataException {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (Exception e) {
            throw new RestApiInvalidDataException("Invalid date format: " + dateString + "  " + e.getMessage());
        }
    }

    public static String formatDate(LocalDate date) {
        return date.format(formatter);
    }
}
