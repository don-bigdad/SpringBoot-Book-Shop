package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.order.RequestOrderDto;
import com.example.springbootbookshop.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public CartDto save(Long userId, RequestOrderDto requestOrderDto) {

        return null;
    }
}
