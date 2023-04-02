package com.deliver.order.statemachine.cqrs;

import org.springframework.statemachine.StateMachineEventResult;
import reactor.core.publisher.Mono;

public interface OutboundResponse<EVENT, RESULT> {

    RESULT result();

    Class<RESULT> resultType();

    EVENT actionType();

    StateMachineEventResult.ResultType status();

    Mono<Void> completeReactively();

    <T> T getHeader(String attrKey, Class<T> type);

    <T> T getVariable(String attrKey, Class<T> type);
}
