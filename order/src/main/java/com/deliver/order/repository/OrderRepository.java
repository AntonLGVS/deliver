package com.deliver.order.repository;

import com.deliver.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Stream<OrderEntity> findByCreatedBy(String createdBy);
}