package com.example.springbootbookshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(@NotNull @Email String email,
                                  @NotNull String password) {
}
