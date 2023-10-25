package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.dto.UserDto;
import com.example.springbootbookshop.dto.UserLoginResponseDto;
import com.example.springbootbookshop.dto.UserRegisterResponseDto;
import com.example.springbootbookshop.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface UserMapper {
    UserDto toDto(User user);

    User toUserModel(UserDto userDto);

    UserRegisterResponseDto toResponseDto(User user);

    UserLoginResponseDto toLoginResponseDto(User user);
}
