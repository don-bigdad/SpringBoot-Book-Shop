package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.cart.item.CartItemDto;
import com.example.springbootbookshop.dto.cart.item.RequestCartItemDto;
import com.example.springbootbookshop.dto.cart.item.UpdateRequestCartItemDto;
import com.example.springbootbookshop.entity.User;
import com.example.springbootbookshop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Cart management",description = "Endpoints for managing carts")
@RestController
@Validated
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get cart by user id from DB")
    @PreAuthorize("hasRole('USER')")
    public CartDto getUserCart(Authentication authentication) {
        return cartService.findById(getUserId(authentication));
    }

    @PostMapping
    @Operation(summary = "Add books to the user cart")
    @PreAuthorize("hasRole('USER')")
    public CartItemDto addItemToCart(Authentication authentication,
                                     @Valid @RequestBody RequestCartItemDto cartItem) {
        return cartService.addItemToCart(getUserId(authentication), cartItem);
    }

    @DeleteMapping("cart-items/{id}")
    @Operation(summary = "Delete cart item from the user cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(@PathVariable @Positive Long id) {
        cartService.removeItem(id);
    }

    @PutMapping("cart-items/{id}")
    @Operation(summary = "Update cart item in the user cart")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public CartItemDto updateItemQuantity(@PathVariable @Positive Long id,
                                          @RequestBody @Valid UpdateRequestCartItemDto quantity) {
        return cartService.updateCartItem(id, quantity);
    }

    private Long getUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
