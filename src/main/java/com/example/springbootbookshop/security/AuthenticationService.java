package com.example.springbootbookshop.security;

import com.example.springbootbookshop.dto.UserLoginRequestDto;
import com.example.springbootbookshop.dto.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticateUser(UserLoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken usernamePassAuthToken =
                new UsernamePasswordAuthenticationToken(
                requestDto.email(), requestDto.password());
        final Authentication authentication = authenticationManager.authenticate(
                usernamePassAuthToken
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
