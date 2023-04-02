package com.deliver.order.service;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.service.order.api.OrderCommandExchanger;
import com.deliver.order.statemachine.cqrs.OutboundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StateMachineFacade implements StateMachineDispatcher {
    private final StateMachineService<OrderBPM.State, OrderBPM.Action> orderStateMachineService;

    @Override
    public <P, R> Mono<OutboundResponse<OrderBPM.Action, R>> sendOrderEvent(OrderCommandExchanger<P, R> request) {
        String id = request.getId() == null ? null : request.getId().toString();

        StateMachine<OrderBPM.State, OrderBPM.Action> stateMachine = orderStateMachineService.acquireStateMachine(id);

        request.applyStateMachine(stateMachine);

        return Mono.just(request.buildMessage())
                .transform(stateMachine::sendEvent)
                .map(request::applyResultEvent);
    }
}
