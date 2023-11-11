package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.dto.cart.item.CartItemDto;
import com.example.springbootbookshop.dto.cart.item.RequestCartItemDto;
import com.example.springbootbookshop.entity.CartItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(RequestCartItemDto cartItemDto);
}
