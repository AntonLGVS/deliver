package com.deliver.order.mapper.kafka;

public interface KafkaMapper<S, T> {
    T toContract(S source);
}
