package com.example.springbootbookshop.dto.order;

import jakarta.validation.constraints.NotBlank;

public record RequestOrderDto(@NotBlank String shippingAddress) {
}
