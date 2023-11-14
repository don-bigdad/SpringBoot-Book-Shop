package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.order.RequestOrderDto;

public interface OrderService {
    CartDto save(Long userId, RequestOrderDto requestOrderDto);
}
