package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.cart.CartDto;
import com.example.springbootbookshop.dto.cart.item.CartItemDto;
import com.example.springbootbookshop.dto.cart.item.RequestCartItemDto;
import com.example.springbootbookshop.dto.cart.item.UpdateRequestCartItemDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Cart;
import com.example.springbootbookshop.entity.CartItem;
import com.example.springbootbookshop.exception.EntityNotFoundException;
import com.example.springbootbookshop.mapper.CartItemMapper;
import com.example.springbootbookshop.mapper.CartMapper;
import com.example.springbootbookshop.repository.BookRepository;
import com.example.springbootbookshop.repository.CartItemsRepository;
import com.example.springbootbookshop.repository.CartRepository;
import com.example.springbootbookshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemsRepository cartItemsRepository;

    @Transactional(readOnly = true)
    @Override
    public CartDto findById(Long id) {
        Cart cart = cartRepository.getCartByUserId(id).orElseThrow(
                () -> new EntityNotFoundException("User with id "
                        + id + "don`t have a cart")
        );
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartItemDto addItemToCart(Long userId, RequestCartItemDto requestCartItemDto) {
        Cart cart = cartRepository.getCartByUserId(userId).get();
        Book book = bookRepository.findById(requestCartItemDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id: " + requestCartItemDto.bookId()
                        + "doesn`t exist"));
        int quantityToAdd = requestCartItemDto.quantity();
        CartItem cartItem = cartItemsRepository.findByCartAndBook(cart, book)
                .orElseGet(() -> {
                    CartItem newCartItem = cartItemMapper.toEntity(requestCartItemDto);
                    newCartItem.setBook(book);
                    newCartItem.setCart(cart);
                    return newCartItem;
                });
        cartItem.setQuantity(quantityToAdd + cartItem.getQuantity());
        return cartItemMapper.toDto(cartItemsRepository.save(cartItem));
    }

    @Override
    public void removeItem(Long itemId) {
        if (!cartItemsRepository.existsById(itemId)) {
            throw new EntityNotFoundException("Item with id: " + itemId
                    + " doesn`t exist");
        }
        cartItemsRepository.deleteById(itemId);
    }

    @Override
    public CartItemDto updateCartItem(Long id, UpdateRequestCartItemDto quantity) {
        CartItem cartItemToUpdate = cartItemsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nothing to update, item with id: "
                + id + " doesn`t exist"));
        cartItemToUpdate.setQuantity(quantity.quantity());
        return cartItemMapper.toDto(cartItemsRepository.save(cartItemToUpdate));
    }
}
