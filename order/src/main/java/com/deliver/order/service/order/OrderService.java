package com.deliver.order.service.order;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Optional<OrderDTO> getOrder(UUID uuid);

    List<OrderDTO> getAllForUser(String username);

    List<OrderDTO> getAll();

    Mono<OrderDTO> createOrder(CreateOrderRequest orderDTO);

    Mono<OrderDTO> editTargetAddress(UUID orderId, String dest);

    Mono<Void> cancelOrder(UUID orderId);

    Mono<Void> sendAction(UUID orderId, OrderBPM.Action action);
}
