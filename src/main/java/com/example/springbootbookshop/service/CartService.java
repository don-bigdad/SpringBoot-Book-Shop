package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.cart.item.CartItemDto;
import com.example.springbootbookshop.dto.cart.item.RequestCartItemDto;
import com.example.springbootbookshop.dto.cart.item.UpdateRequestCartItemDto;

public interface CartService {
    CartDto findById(Long id);

    CartItemDto addItemToCart(Long id, RequestCartItemDto requestCartItemDto);

    void removeItem(Long itemId);

    CartItemDto updateCartItem(Long id, UpdateRequestCartItemDto quantity);
}
