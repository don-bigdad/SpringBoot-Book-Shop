package com.example.springbootbookshop.dto;

public record UserRegisterResponseDto(Long id,
                                      String email,
                                      String firstName,
                                      String lastName,
                                      String shippingAddress) {
}
