package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String message() default "Invalid date format. Please use MM/dd/yyyy";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String pattern(); // specify the date pattern
}