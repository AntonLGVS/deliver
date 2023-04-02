package com.deliver.order.service.order;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.service.order.api.OrderCommandExchanger;

import java.util.UUID;

public class OrderRequestFactory {
    public static OrderCommandExchanger<OrderEntity, OrderEntity> createOrderRequest(OrderEntity order) {
        return new OrderCommandExchanger<OrderEntity, OrderEntity>(OrderBPM.Action.CREATE, OrderEntity.class)
                .setPayload(order);
    }

    public static OrderCommandExchanger<String, OrderEntity> editAddressOrderRequest(UUID orderId, String address) {

        return new OrderCommandExchanger<String, OrderEntity>(OrderBPM.Action.EDIT, OrderEntity.class)
                .setPayload(address)
                .setId(orderId);
    }

    public static OrderCommandExchanger<Void, Void> eventOrderRequest(UUID orderId, OrderBPM.Action event) {
        return new OrderCommandExchanger<Void, Void>(event, Void.class)
                .setId(orderId);
    }
}

