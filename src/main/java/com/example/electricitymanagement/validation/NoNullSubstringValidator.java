
package com.example.electricitymanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoNullSubstringValidator implements ConstraintValidator<NoNullSubstring, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.toLowerCase().contains("null");
    }
}
