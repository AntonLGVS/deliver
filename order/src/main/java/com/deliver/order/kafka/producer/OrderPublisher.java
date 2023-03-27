package com.deliver.order.kafka.producer;

import com.deliver.order.dto.OrderDTO;
import com.deliver.order.mapper.kafka.KafkaMapper;
import lombok.RequiredArgsConstructor;
import org.deliver.order.OrderContract;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderPublisher {
    private final KafkaTemplate<String, OrderContract> producer;
    private final KafkaMapper<OrderDTO, OrderContract> mapper;
    private final KafkaAPI api;

    @TransactionalEventListener
    public void publishCreatedOrder(OrderDTO createdDto) {
        producer.send(api.getOrder().getOrderTopic(), mapper.toContract(createdDto));
    }
}
