package com.example.springbootbookshop.dto;

import com.example.springbootbookshop.annotation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch
public record UserRegistrationRequestDto(@NotBlank String email,
                                         @NotBlank @Size(min = 4) String password,
                                         @NotBlank @Size(min = 4) String repeatPassword,
                                         @NotBlank String firstName,
                                         @NotBlank String lastName,
                                         String shippingAddress) {
}
