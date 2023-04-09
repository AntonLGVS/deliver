package com.deliver.order.service.order.impl;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.mapper.OrderMapper;
import com.deliver.order.repository.OrderRepository;
import com.deliver.order.service.StateMachineDispatcher;
import com.deliver.order.service.order.OrderRequestFactory;
import com.deliver.order.service.order.OrderService;
import com.deliver.order.service.order.api.OrderCommandExchanger;
import com.deliver.order.statemachine.cqrs.OutboundResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final StateMachineDispatcher stateMachineDispatcher;

    @Override
    public Optional<OrderDTO> getOrder(UUID uuid) {
        return repository.findById(uuid).map(mapper::toDto);
    }

    @Override
    @Transactional
    public List<OrderDTO> getAllForUser(String username) {
        try(Stream<OrderEntity> orders = repository.findByCreatedBy(username)) {
            return orders.map(mapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<OrderDTO> getAllForCourier(String username) {
        try(Stream<OrderEntity> orders = repository.findByCourier(username)) {
            return orders.map(mapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<OrderDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Mono<OrderDTO> createOrder(CreateOrderRequest orderDTO) {
        OrderEntity order = mapper.toEntity(orderDTO);
        OrderCommandExchanger<OrderEntity, OrderEntity> request = OrderRequestFactory.createOrderRequest(order);
        Mono<OutboundResponse<OrderBPM.Action, OrderEntity>> pipe = stateMachineDispatcher.sendOrderEvent(request);

        return pipe.mapNotNull(OutboundResponse::result).map(mapper::toDto);
    }

    @Override
    @Transactional
    public Mono<OrderDTO> editTargetAddress(UUID orderId, String dest) {
        OrderCommandExchanger<String, OrderEntity> request = OrderRequestFactory.editAddressOrderRequest(orderId, dest);
        Mono<OutboundResponse<OrderBPM.Action, OrderEntity>> pipe = stateMachineDispatcher.sendOrderEvent(request);

        return pipe.mapNotNull(OutboundResponse::result).map(mapper::toDto);
    }

    @Override
    public Mono<Void> cancelOrder(UUID orderId) {
        OrderCommandExchanger<Void, Void> request = OrderRequestFactory.eventOrderRequest(orderId, OrderBPM.Action.CANCEL);
        return stateMachineDispatcher.sendOrderEvent(request).then();
    }

    @Override
    public Mono<Void> sendAction(UUID orderId, OrderBPM.Action action) {
        OrderCommandExchanger<Void, Void> request = OrderRequestFactory.eventOrderRequest(orderId, action);
        return stateMachineDispatcher.sendOrderEvent(request).then();
    }

    @Override
    public Mono<Boolean> assignCourier(UUID orderId, String courierId) {
        OrderCommandExchanger<String, Boolean> request = OrderRequestFactory.assignCourier(orderId, courierId);
        return stateMachineDispatcher.sendOrderEvent(request).map(OutboundResponse::result);
    }
}
