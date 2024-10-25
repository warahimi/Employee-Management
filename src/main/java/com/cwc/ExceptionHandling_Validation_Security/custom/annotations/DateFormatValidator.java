package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateFormatValidator implements ConstraintValidator<DateFormat, LocalDate> {

    private String datePattern;

    @Override
    public void initialize(DateFormat dateFormat) {
        this.datePattern = dateFormat.pattern();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // Let @NotNull handle null validation
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalDate.parse(date.format(formatter), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}