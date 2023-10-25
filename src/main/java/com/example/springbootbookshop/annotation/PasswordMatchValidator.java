package com.example.springbootbookshop.annotation;

import com.example.springbootbookshop.dto.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(UserRegistrationRequestDto dto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return dto.password().equals(dto.repeatPassword());
    }
}
