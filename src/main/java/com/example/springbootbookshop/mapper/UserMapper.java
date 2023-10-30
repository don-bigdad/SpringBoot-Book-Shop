package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.dto.user.UserDto;
import com.example.springbootbookshop.dto.user.UserRegistrationRequestDto;
import com.example.springbootbookshop.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUserModel(UserRegistrationRequestDto userDto);
}
