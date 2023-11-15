package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Order;
import java.util.Set;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "orderItems"})
    Set<Order> findAllByUserId(Long userId);
}