package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.order.OrderDto;
import com.example.springbootbookshop.dto.order.OrderItemDto;
import com.example.springbootbookshop.dto.order.RequestOrderDto;
import com.example.springbootbookshop.dto.order.UpdateRequestOrderStatus;
import com.example.springbootbookshop.entity.User;
import com.example.springbootbookshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Order management",description = "Endpoints make orders")
@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Make order")
    @PreAuthorize("hasRole('USER')")
    public CartDto makeOrder(Authentication authentication,
                             @RequestBody RequestOrderDto requestOrderDto) {
        return orderService.save(((User) authentication.getPrincipal()).getId(), requestOrderDto);
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "Update order status (for admin only)")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateStatus(@Positive @PathVariable Long orderId,
                             @RequestBody UpdateRequestOrderStatus updateStatus) {
        orderService.updateOrderStatus(orderId, updateStatus);
    }

    @GetMapping
    @Operation(summary = "Get order history")
    @PreAuthorize("hasRole('USER')")
    public Set<OrderDto> getOrderHistory(Authentication authentication,
                                         @PageableDefault(size = 5, page = 0)
                                         Pageable pageable) {
        return orderService.getAllOrders(((User) authentication.getPrincipal()).getId(), pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get special item from order history")
    @PreAuthorize("hasRole('USER')")
    public OrderItemDto getOrderItem(Authentication authentication,
                                      @PathVariable @Positive Long orderId,
                                      @PathVariable @Positive Long itemId) {
        return orderService.getOrderItem(((User) authentication.getPrincipal()).getId(),
                orderId, itemId);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get special order from user history")
    @PreAuthorize("hasRole('USER')")
    public Set<OrderItemDto> getOrder(Authentication authentication,
                                       @PathVariable Long orderId) {
        return orderService.getOrder(((User) authentication.getPrincipal()).getId(), orderId);
    }
}
