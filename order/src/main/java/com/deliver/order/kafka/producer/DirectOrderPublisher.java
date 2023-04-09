package com.deliver.order.kafka.producer;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.kafka.KafkaAPI;
import com.deliver.order.mapper.contract.CourierManagementMapper;
import com.deliver.order.mapper.contract.OrderContractMapper;
import lombok.RequiredArgsConstructor;
import org.deliver.courier.CourierManagementContract;
import org.deliver.order.OrderContract;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectOrderPublisher implements OrderPublisher {
    private final KafkaTemplate<String, Object> producer;
    private final OrderContractMapper orderContractMapper;
    private final CourierManagementMapper courierManagementMapper;
    private final KafkaAPI api;

    @Override
    public void publishOrder(OrderEntity order) {
        String topic = api.getOrder().getOrderTopic();
        OrderContract orderContract = orderContractMapper.toOrderContract(order);
        producer.send(topic, orderContract);
    }

    @Override
    public void engageCourier(OrderEntity order) {
        publishOrder(order);

        String topic = api.getOrder().getCourierManagement();
        CourierManagementContract courierClaim = courierManagementMapper.acquireCourierContract(order);
        producer.send(topic, courierClaim);
    }

    @Override
    public void terminateOrder(OrderEntity order) {
        publishOrder(order);

        String topic = api.getOrder().getCourierManagement();
        CourierManagementContract courierClaim = courierManagementMapper.releaseCourierContract(order);
        producer.send(topic, courierClaim);
    }
}
