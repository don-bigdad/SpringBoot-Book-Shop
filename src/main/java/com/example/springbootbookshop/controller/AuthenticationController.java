package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.user.UserLoginRequestDto;
import com.example.springbootbookshop.dto.user.UserLoginResponseDto;
import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.dto.user.UserResponseDto;
import com.example.springbootbookshop.exception.RegistrationException;
import com.example.springbootbookshop.security.AuthenticationService;
import com.example.springbootbookshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Registration/Login",description = "Endpoints for registration or login")
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    @Operation(summary = "Register user into system")
    public UserResponseDto register(@RequestBody @Valid
                                UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Login new user to DB")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticateUser(requestDto);
    }
}
