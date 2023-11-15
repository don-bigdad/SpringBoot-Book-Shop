package com.example.springbootbookshop.dto.cart.item;

import jakarta.validation.constraints.Positive;

public record RequestCartItemDto(@Positive Long bookId,
                                 @Positive Integer quantity) {
}
