package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented

@Constraint(validatedBy = MinAgeValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {

    int value();

    String message() default "Age must be at least {value} years old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}