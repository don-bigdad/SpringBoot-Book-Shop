package com.example.springbootbookshop.dto.cart;

import com.example.springbootbookshop.dto.cart.item.CartItemDto;
import java.util.Set;

public record CartDto(Long id,
                      Long userId,
                      Set<CartItemDto> cartItems) {
}
