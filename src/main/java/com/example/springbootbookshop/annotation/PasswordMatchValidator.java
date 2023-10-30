package com.example.springbootbookshop.annotation;

import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;

public class PasswordMatchValidator implements ConstraintValidator<FieldMatch,
        UserRegistrationRequestDto> {

    @Override
    public boolean isValid(@NotNull UserRegistrationRequestDto dto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return dto.password().equals(dto.repeatPassword());
    }
}
