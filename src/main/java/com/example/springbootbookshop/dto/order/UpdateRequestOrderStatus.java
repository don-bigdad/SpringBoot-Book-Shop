package com.example.springbootbookshop.dto.order;

import com.example.springbootbookshop.entity.Status;
import jakarta.validation.constraints.NotBlank;

public record UpdateRequestOrderStatus(@NotBlank Status status) {
}
