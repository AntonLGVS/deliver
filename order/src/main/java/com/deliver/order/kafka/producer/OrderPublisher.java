package com.deliver.order.kafka.producer;

import com.deliver.order.domain.OrderEntity;

public interface OrderPublisher {
    void publishOrder(OrderEntity createdDto);
}
