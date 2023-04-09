package com.deliver.order.mapper;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "courier", ignore = true)
    OrderEntity toEntity(CreateOrderRequest orderRequest);

    OrderDTO toDto(OrderEntity order);
}