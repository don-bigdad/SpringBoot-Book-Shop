package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"user", "orderItems"})
    Optional<Order> findOrderByIdAndUserId(Long orderId, Long userId);

    @EntityGraph(attributePaths = {"user", "orderItems"})
    List<Order> findAllByUserId(Long userId, Pageable pageable);
}
