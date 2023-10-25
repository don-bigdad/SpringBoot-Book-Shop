package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.UserDto;
import com.example.springbootbookshop.dto.UserRegistrationRequestDto;
import com.example.springbootbookshop.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
