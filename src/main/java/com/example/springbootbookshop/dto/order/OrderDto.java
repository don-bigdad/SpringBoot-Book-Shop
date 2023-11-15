package com.example.springbootbookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDto(Long id,
                       Long userId,
                       String status,
                       Set<OrderItemDto> orderItems,
                       BigDecimal total,
                       LocalDateTime orderDate,
                       String shippingAddress) {
}
