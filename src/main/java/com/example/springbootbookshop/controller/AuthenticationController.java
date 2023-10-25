package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.UserLoginRequestDto;
import com.example.springbootbookshop.dto.UserLoginResponseDto;
import com.example.springbootbookshop.dto.UserRegisterResponseDto;
import com.example.springbootbookshop.dto.UserRegistrationRequestDto;
import com.example.springbootbookshop.exception.RegistrationException;
import com.example.springbootbookshop.security.AuthenticationService;
import com.example.springbootbookshop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    public UserRegisterResponseDto register(@RequestBody @Valid
                                                UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping(value = "/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
