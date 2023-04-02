package com.deliver.order.statemachine.order;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.statemachine.order.spi.OrderDataAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.web.context.WebApplicationContext;

import java.util.EnumSet;

@EnableStateMachineFactory
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderBPM.State, OrderBPM.Action> {
    private final OrderDataAccessor accessor;

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderBPM.State, OrderBPM.Action> transitions) throws Exception {
        transitions
                // GREEN WAY
                .withInternal()
                .event(OrderBPM.Action.CREATE)
                .source(OrderBPM.State.NEW)
                .and()
                .withInternal()
                .event(OrderBPM.Action.EDIT)
                .source(OrderBPM.State.NEW)
                .and()
                .withExternal()
                .event(OrderBPM.Action.ASSIGN)
                .source(OrderBPM.State.NEW).target(OrderBPM.State.IN_PROGRESS)
                .and()
                .withExternal()
                .event(OrderBPM.Action.DELIVER)
                .source(OrderBPM.State.IN_PROGRESS).target(OrderBPM.State.DELIVERED)
                .and()
                // RED WAY
                .withExternal()
                .event(OrderBPM.Action.CANCEL)
                .source(OrderBPM.State.NEW).target(OrderBPM.State.CANCELLED)
                .and()
                .withLocal()
                .event(OrderBPM.Action.LOST)
                .source(OrderBPM.State.IN_PROGRESS).target(OrderBPM.State.CANCELLED);
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderBPM.State, OrderBPM.Action> states) throws Exception {
        states.withStates()
                .initial(OrderBPM.State.NEW)
                .end(OrderBPM.State.DELIVERED)
                .end(OrderBPM.State.CANCELLED)
                .states(EnumSet.allOf(OrderBPM.State.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderBPM.State, OrderBPM.Action> config) throws Exception {
        config.withPersistence()
                .runtimePersister(accessor);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public StateMachineService<OrderBPM.State, OrderBPM.Action> orderStateMachineService(
            StateMachineFactory<OrderBPM.State, OrderBPM.Action> factory,
            OrderDataAccessor dataAccessor) {

        return new DefaultStateMachineService<>(factory, dataAccessor);
    }
}
