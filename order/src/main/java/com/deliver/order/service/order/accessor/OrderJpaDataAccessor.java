package com.deliver.order.service.order.accessor;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.repository.OrderRepository;
import com.deliver.order.statemachine.order.spi.OrderDataAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderJpaDataAccessor extends AbstractPersistingStateMachineInterceptor<OrderBPM.State, OrderBPM.Action, String>
        implements OrderDataAccessor {

    private final OrderRepository repository;

    @Override
    public void write(StateMachineContext<OrderBPM.State, OrderBPM.Action> context, String orderId) {
        if (orderId == null) {
            return;
        }

        OrderEntity order = context.getExtendedState().get(OrderEntity.class.getName(), OrderEntity.class);

        if (order != null) {
            order.setState(context.getState());
            repository.save(order);
        }
    }

    @Override
    public StateMachineContext<OrderBPM.State, OrderBPM.Action> read(String orderId) {
        DefaultExtendedState extendedState = new DefaultExtendedState();
        if (orderId == null) {
            return new DefaultStateMachineContext<>(null, null, null, extendedState);
        }

        Optional<OrderEntity> order = repository.findById(UUID.fromString(orderId));

        if (order.isEmpty()) {
            return new DefaultStateMachineContext<>(null, null, null, extendedState);
        }

        extendedState.getVariables()
                .putIfAbsent(OrderEntity.class.getName(), order.get());

        return new DefaultStateMachineContext<>(order.get().getState(), null, null, extendedState, null, orderId);
    }

    @Override
    public StateMachineInterceptor<OrderBPM.State, OrderBPM.Action> getInterceptor() {
        return this;
    }
}
