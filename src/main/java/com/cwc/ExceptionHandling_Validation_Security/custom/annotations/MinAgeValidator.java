package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    private int minAge;

    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // Consider using @NotNull separately if null is not allowed
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= minAge;
    }
}