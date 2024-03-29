package com.deliver.order.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("topic")
@Getter
@AllArgsConstructor
public class KafkaAPI {

    @NestedConfigurationProperty
    private Order order;

    @Getter
    @AllArgsConstructor
    public static class Order {
        private String orderTopic;
        private String courierManagement;
        private String orderStatusIn;
    }
}
