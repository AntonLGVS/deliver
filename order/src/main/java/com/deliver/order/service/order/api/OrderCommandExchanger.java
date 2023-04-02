package com.deliver.order.service.order.api;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.statemachine.cqrs.CommandExchanger;

public class OrderCommandExchanger<PAYLOAD, RESULT>
        extends CommandExchanger<OrderBPM.State, OrderBPM.Action, PAYLOAD, RESULT, OrderCommandExchanger<PAYLOAD, RESULT>> {

    public OrderCommandExchanger(OrderBPM.Action action, Class<RESULT> resultType) {
        super(action, resultType);
    }
}
