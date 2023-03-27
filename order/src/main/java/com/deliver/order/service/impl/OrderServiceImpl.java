package com.deliver.order.service.impl;

import com.deliver.order.bpm.OrderState;
import com.deliver.order.domain.Order;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.mapper.OrderMapper;
import com.deliver.order.repository.OrderRepository;
import com.deliver.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final ApplicationEventPublisher publisher;

    @Override
    public Optional<OrderDTO> getOrder(UUID uuid) {
        return repository.findById(uuid).map(mapper::toDto);
    }

    @Override
    public List<OrderDTO> getAllForCurrentUser(String username) {
        return repository.findByCreatedBy(username)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest orderDTO) {
        Order order = mapper.toEntity(orderDTO);
        Order savedOrder = repository.save(order);
        OrderDTO dto = mapper.toDto(savedOrder);
        publisher.publishEvent(dto);
        return dto;
    }

    @Override
    @Transactional
    public Optional<OrderDTO> setTarget(UUID orderId, String dest) {
        return repository.findById(orderId)
                .map(order -> {
                    order.setTargetAddress(dest);
                    Order savedOrder = repository.save(order);
                    OrderDTO dto = mapper.toDto(savedOrder);
                    publisher.publishEvent(dto);
                    return dto;
                });

    }

    @Override
    @Transactional
    public Optional<OrderDTO> setOrderState(UUID orderId, OrderState cancelled) {
        return repository.findById(orderId)
                .map(order -> {
                    order.setState(cancelled);
                    Order savedOrder = repository.save(order);
                    return mapper.toDto(savedOrder);
                });
    }
}
