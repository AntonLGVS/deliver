package com.deliver.order.mapper.kafka;

import com.deliver.order.dto.OrderDTO;
import org.deliver.order.OrderContract;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderKafkaMapper extends KafkaMapper<OrderDTO, OrderContract> {

    @Override
    OrderContract toContract(OrderDTO source);
}

