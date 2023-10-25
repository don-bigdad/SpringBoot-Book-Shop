package com.example.springbootbookshop.dto;

public record UserDto(Long id,
                      String email,
                      String firstName,
                      String lastName,
                      String shippingAddress) {
}
