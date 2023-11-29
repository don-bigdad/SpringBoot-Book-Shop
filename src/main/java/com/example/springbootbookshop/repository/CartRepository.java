package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @EntityGraph(attributePaths = {"user","cartItems"})
    Optional<Cart> getCartByUserId(Long id);
}
