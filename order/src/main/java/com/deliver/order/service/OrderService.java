package com.deliver.order.service;

import com.deliver.order.bpm.OrderState;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Optional<OrderDTO> getOrder(UUID uuid);

    List<OrderDTO> getAllForCurrentUser(String username);

    List<OrderDTO> getAll();

    OrderDTO createOrder(CreateOrderRequest orderDTO);

    Optional<OrderDTO> setTarget(UUID orderId, String dest);

    Optional<OrderDTO> setOrderState(UUID orderId, OrderState cancelled);
}
