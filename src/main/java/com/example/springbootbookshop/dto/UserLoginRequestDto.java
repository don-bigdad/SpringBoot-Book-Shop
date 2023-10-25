package com.example.springbootbookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(@NotBlank String email,
                                  @NotBlank @Size(min = 4) String password) {
}
