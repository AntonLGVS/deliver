package com.deliver.order.statemachine.order.spi;

import com.deliver.order.domain.common.OrderBPM;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

public interface OrderDataAccessor extends StateMachineRuntimePersister<OrderBPM.State, OrderBPM.Action, String> {
}
