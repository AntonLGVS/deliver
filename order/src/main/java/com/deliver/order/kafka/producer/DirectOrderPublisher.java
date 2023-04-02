package com.deliver.order.kafka.producer;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.kafka.KafkaAPI;
import com.deliver.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.deliver.order.OrderContract;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectOrderPublisher implements OrderPublisher {
    private final KafkaTemplate<String, OrderContract> producer;
    private final OrderMapper mapper;
    private final KafkaAPI api;

    @Override
    public void publishOrder(OrderEntity createdDto) {
        producer.send(api.getOrder().getOrderTopicOut(), mapper.toContract(createdDto));
    }
}
