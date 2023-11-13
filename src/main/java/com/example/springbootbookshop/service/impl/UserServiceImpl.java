package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.dto.user.UserResponseDto;
import com.example.springbootbookshop.entity.Cart;
import com.example.springbootbookshop.entity.Role;
import com.example.springbootbookshop.entity.RoleName;
import com.example.springbootbookshop.entity.User;
import com.example.springbootbookshop.exception.RegistrationException;
import com.example.springbootbookshop.mapper.UserMapper;
import com.example.springbootbookshop.repository.CartRepository;
import com.example.springbootbookshop.repository.RoleRepository;
import com.example.springbootbookshop.repository.UserRepository;
import com.example.springbootbookshop.service.UserService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
                    throws RegistrationException {
        if (userRepository.existsUserByEmail(requestDto.email())) {
            throw new RegistrationException("User with email: "
                    + requestDto.email() + " already exists");
        }
        User user = userMapper.toUserModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(getUserRole());
        setShoppingCartForUser(userRepository.save(user));
        return userMapper.toUserDto(user);
    }

    private void setShoppingCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    private Set<Role> getUserRole() {
        return new HashSet<>(Collections.singletonList(
                roleRepository.findRoleByName(RoleName.USER)
        ));
    }
}
