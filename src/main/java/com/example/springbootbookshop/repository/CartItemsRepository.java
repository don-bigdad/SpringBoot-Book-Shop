package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Cart;
import com.example.springbootbookshop.entity.CartItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = "book")
    Optional<CartItem> findByCartAndBook(Cart cart, Book book);

    Optional<CartItem> getCartItemsByIdAndCartId(Long itemId,Long cartId);

    Set<CartItem> findByCart(Cart cart);
}
