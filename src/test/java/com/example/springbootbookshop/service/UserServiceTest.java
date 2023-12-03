package com.example.springbootbookshop.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.dto.user.UserResponseDto;
import com.example.springbootbookshop.entity.Role;
import com.example.springbootbookshop.entity.RoleName;
import com.example.springbootbookshop.entity.User;
import com.example.springbootbookshop.exception.RegistrationException;
import com.example.springbootbookshop.mapper.impl.UserMapperImpl;
import com.example.springbootbookshop.repository.CartRepository;
import com.example.springbootbookshop.repository.RoleRepository;
import com.example.springbootbookshop.repository.UserRepository;
import com.example.springbootbookshop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapperImpl userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("User registration successful")
    void userRegistrationSuccess() throws RegistrationException {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "newemail@example.com", "password", "password", "User1", "User1", null
        );
        when(userRepository.existsUserByEmail(requestDto.email())).thenReturn(false);
        when(roleRepository.findRoleByName(RoleName.USER)).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserResponseDto responseDto = userService.register(requestDto);

        assertNotNull(responseDto);
    }

    @Test
    @DisplayName("User registration fails due to existing email")
    void userRegistrationFailsDueToExistingEmail() {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(
                "existingemail@example.com",
                "password", "password",
                "User1", "User1", null
        );
        when(userRepository.existsUserByEmail(requestDto.email())).thenReturn(true);

        assertThrows(RegistrationException.class, () -> userService.register(requestDto));
    }
}
