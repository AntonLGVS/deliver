package com.deliver.order.service;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.service.order.api.OrderCommandExchanger;
import com.deliver.order.statemachine.cqrs.OutboundResponse;
import reactor.core.publisher.Mono;

public interface StateMachineDispatcher {

    <P, R> Mono<OutboundResponse<OrderBPM.Action, R>> sendOrderEvent(OrderCommandExchanger<P, R> request);
}