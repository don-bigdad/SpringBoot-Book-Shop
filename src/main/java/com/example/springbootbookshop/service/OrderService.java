package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.order.OrderDto;
import com.example.springbootbookshop.dto.order.OrderItemDto;
import com.example.springbootbookshop.dto.order.RequestOrderDto;
import com.example.springbootbookshop.dto.order.UpdateRequestOrderStatus;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    CartDto save(Long userId, RequestOrderDto requestOrderDto);

    OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId);

    Set<OrderItemDto> getOrder(Long userId, Long orderId);

    Set<OrderDto> getAllOrders(Long userId, Pageable pageable);

    void updateOrderStatus(Long orderId, UpdateRequestOrderStatus status);
}
