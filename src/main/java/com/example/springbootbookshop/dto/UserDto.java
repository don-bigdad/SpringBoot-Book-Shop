package com.example.springbootbookshop.dto;

import com.example.springbootbookshop.entity.Role;
import java.util.Set;

public record UserDto(Long id,
                     String email,
                     String firstName,
                     String lastName,
                     String shippingAddress,
                     Set<Role> roles) {
}
