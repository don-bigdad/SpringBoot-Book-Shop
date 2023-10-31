package com.example.springbootbookshop.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateBookRequestDto(@NotBlank String title,
                                   @NotBlank String author,
                                   @NotBlank String isbn,
                                   @NotBlank @PositiveOrZero BigDecimal price,
                                   String description,
                                   String coverImage) {
}
