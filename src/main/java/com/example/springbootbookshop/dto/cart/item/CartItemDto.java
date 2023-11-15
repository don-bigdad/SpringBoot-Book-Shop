package com.example.springbootbookshop.dto.cart.item;

public record CartItemDto(Long id,
                          Long bookId,
                          String bookTitle,
                          Integer quantity){
}
