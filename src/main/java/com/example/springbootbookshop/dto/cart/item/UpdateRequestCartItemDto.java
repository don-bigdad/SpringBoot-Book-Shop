package com.example.springbootbookshop.dto.cart.item;

import jakarta.validation.constraints.Positive;

public record UpdateRequestCartItemDto(@Positive Integer quantity) {
}
