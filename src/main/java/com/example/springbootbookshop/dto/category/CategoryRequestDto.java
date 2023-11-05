package com.example.springbootbookshop.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CategoryRequestDto(@NotBlank @Pattern(regexp = "^[a-zA-Z]+$") String name,
                                 @NotBlank String description) {
}
