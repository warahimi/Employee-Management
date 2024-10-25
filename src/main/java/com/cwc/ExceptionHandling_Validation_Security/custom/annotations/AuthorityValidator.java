package com.cwc.ExceptionHandling_Validation_Security.custom.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class AuthorityValidator implements ConstraintValidator<ValidAuthority, List<String>> {

    private static final Set<String> VALID_AUTHORITIES = new HashSet<>(Arrays.asList("USER", "ADMIN", "DEVELOPER", "MANAGER"));

    @Override
    public boolean isValid(List<String> authorities, ConstraintValidatorContext context) {
        if (authorities == null || authorities.isEmpty()) {
            return false;
        }

        for (String authority : authorities) {
            if (!VALID_AUTHORITIES.contains(authority)) {
                return false;
            }
        }

        return true;
    }
}