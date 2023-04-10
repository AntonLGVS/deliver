package com.deliver.order.service.order.impl;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.kafka.producer.OrderPublisher;
import com.deliver.order.repository.OrderRepository;
import com.deliver.order.service.order.api.OrderCommandExchanger;
import com.deliver.order.statemachine.cqrs.CtxAttributes;
import com.deliver.order.statemachine.order.spi.annotation.OrderOnTransition;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeader;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@WithStateMachine
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository repository;
    private final OrderPublisher publisher;

    @OrderOnTransition(source = OrderBPM.State.NEW)
    @Transactional
    public void createEvent(
            @EventHeader(CtxAttributes.PAYLOAD) OrderEntity order,
            @EventHeader(CtxAttributes.REQUEST) OrderCommandExchanger<OrderEntity, OrderEntity> exchanger,
            StateMachine<OrderBPM.State, OrderBPM.Action> stateMachine) {

        order.setState(stateMachine.getInitialState().getId());
        order.setId(stateMachine.getUuid());
        OrderEntity savedOrder = repository.save(order);

        // Publish CREATED ORDER to KAFKA
        publisher.publishOrder(savedOrder);

        exchanger.applyResult(savedOrder);
    }

    @OrderOnTransition(source = OrderBPM.State.NEW)
    @Transactional
    public void editAddress(
            @EventHeader(value = CtxAttributes.PAYLOAD) String address,
            @EventHeader(value = CtxAttributes.REQUEST) OrderCommandExchanger<String, OrderEntity> exchanger) {

        repository.findById(exchanger.getId())
                .ifPresent(order -> {
                    order.setTargetAddress(exchanger.getPayload());
                    OrderEntity savedOrder = repository.save(order);

                    // Publish CREATED ORDER to KAFKA
                    publisher.publishOrder(savedOrder);

                    exchanger.applyResult(savedOrder);
                });
    }

    @OrderOnTransition(source = OrderBPM.State.NEW, target = OrderBPM.State.IN_PROGRESS)
    public void assignCourier(@EventHeader(value = CtxAttributes.PAYLOAD) UUID courierId,
                              @EventHeader(value = CtxAttributes.REQUEST) OrderCommandExchanger<String, OrderEntity> exchanger) {

        repository.findById(exchanger.getId())
                .ifPresent(order -> {
                    order.setCourier(courierId);

                    // Acquire courier
                    publisher.engageCourier(order);
        });
    }

    @OrderOnTransition(target = OrderBPM.State.CANCELLED)
    @Transactional
    public void cancelOrder(@EventHeader(value = CtxAttributes.PAYLOAD_ID) UUID orderId) {

        // Release courier
        repository.findById(orderId)
                .ifPresent(publisher::terminateOrder);
    }
}