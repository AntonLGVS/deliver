package com.deliver.order.repository;

import com.deliver.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Stream<Order> findByCreatedBy(String createdBy);
}