package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Order;
import com.example.springbootbookshop.entity.OrderItem;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderAndId(Order order, Long id);

    Set<OrderItem> findByOrder(Order order);
}
