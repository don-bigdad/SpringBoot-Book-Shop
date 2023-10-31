package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.dto.user.UserResponseDto;
import com.example.springbootbookshop.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
