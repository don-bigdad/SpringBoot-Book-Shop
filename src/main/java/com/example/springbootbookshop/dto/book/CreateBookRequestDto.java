package com.example.springbootbookshop.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(@NotBlank String title,
                                   @NotBlank String author,
                                   @NotBlank String isbn,
                                   @NotNull @PositiveOrZero BigDecimal price,
                                   String description,
                                   String coverImage,
                                   @NotEmpty List<@Positive Long> categoryIds) {
}
