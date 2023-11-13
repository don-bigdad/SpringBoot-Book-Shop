package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.cart.item.RequestCartItemDto;
import com.example.springbootbookshop.entity.Cart;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl",
        uses = CartItemMapper.class
)
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDto toDto(Cart cart);

    Cart toEntity(RequestCartItemDto cart);
}
