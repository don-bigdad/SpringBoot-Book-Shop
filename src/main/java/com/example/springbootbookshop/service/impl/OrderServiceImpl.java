package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.order.OrderDto;
import com.example.springbootbookshop.dto.order.OrderItemDto;
import com.example.springbootbookshop.dto.order.RequestOrderDto;
import com.example.springbootbookshop.dto.order.UpdateRequestOrderStatus;
import com.example.springbootbookshop.entity.Cart;
import com.example.springbootbookshop.entity.CartItem;
import com.example.springbootbookshop.entity.Order;
import com.example.springbootbookshop.entity.OrderItem;
import com.example.springbootbookshop.entity.Status;
import com.example.springbootbookshop.exception.EntityNotFoundException;
import com.example.springbootbookshop.mapper.CartMapper;
import com.example.springbootbookshop.mapper.OrderItemMapper;
import com.example.springbootbookshop.mapper.OrderMapper;
import com.example.springbootbookshop.repository.CartItemsRepository;
import com.example.springbootbookshop.repository.CartRepository;
import com.example.springbootbookshop.repository.OrderItemsRepository;
import com.example.springbootbookshop.repository.OrderRepository;
import com.example.springbootbookshop.repository.UserRepository;
import com.example.springbootbookshop.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemsRepository cartItemsRepository;
    private final CartMapper cartMapper;
    private final OrderItemsRepository orderItemsRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public CartDto save(Long userId, RequestOrderDto requestOrderDto) {
        Cart cart = cartRepository.getCartByUserId(userId).get();
        if (cart.getCartItems().isEmpty()) {
            throw new EntityNotFoundException("You cart is empty");
        }
        Order order = new Order();
        setOrderFields(order, userId, cart,requestOrderDto.shippingAddress());

        orderRepository.save(order);
        Set<CartItem> cartItems = cartItemsRepository.findByCart(cart);
        saveCartItems(cartItems,order);
        cartItemsRepository.deleteAll(cartItems);
        return cartMapper.toDto(cart);
    }

    @Override
    public OrderItemDto getOrderItem(Long userId, Long orderId, Long itemId) {
        Order userOrder = orderRepository.findOrderByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User doen`t have order with id: " + orderId));
        return orderItemMapper.toDto(orderItemsRepository.findByOrderAndId(userOrder, itemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item with id: " + itemId + " doesn`t found")));
    }

    @Override
    public Set<OrderItemDto> getOrder(Long userId, Long orderId) {
        Order orderItemsUser = orderRepository.findOrderByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("No order with id: " + orderId
                        + " for user with id: " + userId));
        return orderItemsRepository.findByOrder(orderItemsUser).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> getAllOrders(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId,
                                  UpdateRequestOrderStatus status) {
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(status.status());
    }

    private void saveCartItems(Set<CartItem> cartItems,Order order) {
        cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setBook(cartItem.getBook());
                    orderItem.setPrice(cartItem.getBook().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    orderItem.setQuantity(cartItem.getQuantity());
                    return orderItem;
                })
                .forEach(orderItemsRepository::save);
    }

    private void setOrderFields(Order order, Long userId, Cart cart, String address) {
        order.setUser(userRepository.getReferenceById(userId));
        order.setShippingAddress(address);
        order.setTotal(calculateTotal(cart));
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(cartItem ->
                        BigDecimal.valueOf(cartItem.getQuantity())
                                .multiply(cartItem.getBook().getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
