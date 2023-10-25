package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.UserRegisterResponseDto;
import com.example.springbootbookshop.dto.UserRegistrationRequestDto;
import com.example.springbootbookshop.exception.RegistrationException;

public interface UserService {
    UserRegisterResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
