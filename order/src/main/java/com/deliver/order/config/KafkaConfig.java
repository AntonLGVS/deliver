package com.deliver.order.config;

import com.deliver.order.kafka.KafkaAPI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KafkaAPI.class)
public class KafkaConfig {
}
