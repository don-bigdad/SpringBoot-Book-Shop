package com.example.springbootbookshop.dto.order;

public record OrderItemDto(Long id,
                           Long bookId,
                           Integer quantity){
}
