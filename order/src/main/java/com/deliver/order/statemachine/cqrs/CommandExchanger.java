package com.deliver.order.statemachine.cqrs;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;


// TO DO: FLUENT API
public class CommandExchanger<STATE, EVENT, PAYLOAD, RESULT, SELF extends CommandExchanger<STATE, EVENT, PAYLOAD, RESULT, SELF>>
        implements Message<PAYLOAD>, OutboundResponse<EVENT, RESULT> {

    private final EVENT action;
    private final MessageHeaderAccessor headers = new MessageHeaderAccessor();
    private UUID id;
    private PAYLOAD payload;
    private StateMachineEventResult<STATE, EVENT> resultEvent;
    private StateMachine<STATE, EVENT> stateMachine;
    private RESULT result;
    private final Class<RESULT> resultType;

    public CommandExchanger(EVENT action, Class<RESULT> resultType) {
        this.action = Objects.requireNonNull(action, "Parameter 'action' must not be null.");
        this.resultType = Objects.requireNonNull(resultType, "Parameter 'resultType' must not be null.");
    }

    public Message<EVENT> buildMessage() {
        return MessageBuilder.withPayload(action)
                .setHeaders(headers)
                .setHeader(CtxAttributes.PAYLOAD_ID, getId())
                .setHeader(CtxAttributes.REQUEST, this)
                .build();
    }

    @Override
    public PAYLOAD getPayload() {
        return payload;
    }

    @Override
    public MessageHeaders getHeaders() {
        return headers.getMessageHeaders();
    }

    @SuppressWarnings("unchecked")
    public SELF setAttribute(String key, Object value) {
        Objects.requireNonNull(key, "Parameter 'key' must not be null.");
        headers.setHeader(key, value);
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF setPayload(PAYLOAD payload) {
        if (payload != null) {
            setAttribute(CtxAttributes.PAYLOAD, payload);
        }
        this.payload = payload;
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF setId(UUID id) {
        if (id != null) {
            setAttribute(CtxAttributes.PAYLOAD_ID, id);
        }
        this.id = id;
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF applyStateMachine(StateMachine<STATE, EVENT> stateMachine) {
        this.stateMachine = Objects.requireNonNull(stateMachine, "Parameter 'stateMachine' must not be null.");
        if (stateMachine.getId() != null) {
            setId(UUID.fromString(stateMachine.getId()));
        }
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF applyResultEvent(StateMachineEventResult<STATE, EVENT> resultEvent) {
        this.resultEvent = Objects.requireNonNull(resultEvent, "Parameter 'resultEvent' must not be null.");
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF applyResult(RESULT result) {
        if (stateMachine != null) {
            stateMachine.getExtendedState().getVariables().put(CtxAttributes.RESULT, result);
        }
        this.result = result;
        return (SELF) this;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public RESULT result() {
        return result;
    }

    @Override
    public Class<RESULT> resultType() {
        return resultType;
    }

    @Override
    public EVENT actionType() {
        return resultEvent.getMessage().getPayload();
    }

    @Override
    public StateMachineEventResult.ResultType status() {
        return resultEvent.getResultType();
    }

    @Override
    public Mono<Void> completeReactively() {
        return resultEvent.complete();
    }

    @Override
    public <T> T getHeader(String attrKey, Class<T> type) {
        return resultEvent.getMessage().getHeaders().get(attrKey, type);
    }

    @Override
    public <T> T getVariable(String attrKey, Class<T> type) {
        return stateMachine.getExtendedState().get(attrKey, type);
    }
}
