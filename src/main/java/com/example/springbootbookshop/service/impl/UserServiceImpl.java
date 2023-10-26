package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.UserDto;
import com.example.springbootbookshop.dto.UserRegistrationRequestDto;
import com.example.springbootbookshop.entity.Role;
import com.example.springbootbookshop.entity.RoleName;
import com.example.springbootbookshop.entity.User;
import com.example.springbootbookshop.exception.RegistrationException;
import com.example.springbootbookshop.mapper.UserMapper;
import com.example.springbootbookshop.repository.RoleRepository;
import com.example.springbootbookshop.repository.UserRepository;
import com.example.springbootbookshop.service.UserService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.email()).isPresent()) {
            throw new RegistrationException("User with email: " + requestDto.email()
            + " already exist");
        }
        User user = userMapper.toUserModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));

        user.setRoles(getUserRole());
        return userMapper.toDto(userRepository.save(user));
    }

    private Set<Role> getUserRole() {
        return new HashSet<>(Collections.singletonList(
                roleRepository.findRoleByName(RoleName.USER)
        ));
    }
}