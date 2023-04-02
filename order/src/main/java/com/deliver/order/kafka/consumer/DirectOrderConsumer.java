package com.deliver.order.kafka.consumer;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.deliver.tracker.TrackerStatusContract;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectOrderConsumer implements OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "#{@'topic-com.deliver.order.kafka.KafkaAPI'.order.orderStatusIn}")
    public void trackerStatusByOrder(TrackerStatusContract contract) {
        UUID orderId = contract.getOrderId();
        TrackerStatusContract.Event event = contract.getEvent();

        log.info("Consumed Tracked contract: id={}, action={}", orderId, contract.getEvent());

        if (event == null || orderId == null) {
            return;
        }

        Mono<Void> subscriber = switch (event) {
            case LOST, RETURNED -> orderService.cancelOrder(orderId);
            case DELIVERED -> orderService.sendAction(orderId, OrderBPM.Action.DELIVER);
        };

        subscriber.subscribe(_void -> log.info("Processed order ID={} EVENT={}", orderId, event));
    }
}
