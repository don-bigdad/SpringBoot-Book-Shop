package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.user.UserDto;
import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.exception.RegistrationException;

public interface UserService {
    UserDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
