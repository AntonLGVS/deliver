package com.deliver.order.mapper.contract;

import com.deliver.order.domain.OrderEntity;
import org.deliver.courier.CourierManagementContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourierManagementMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "courier", target = "courierId")
    @Mapping(target = "status", constant = "ACQUIRED")
    CourierManagementContract acquireCourierContract(OrderEntity order);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "courier", target = "courierId")
    @Mapping(target = "status", constant = "RELEASED")
    CourierManagementContract releaseCourierContract(OrderEntity order);
}
