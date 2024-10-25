package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthorityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAuthority {
    String message() default "Invalid authority provided. Must be one of USER, ADMIN, DEVELOPER, or MANAGER.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}