package com.deliver.order.mapper;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import org.deliver.order.OrderContract;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderEntity toEntity(OrderDTO orderDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderEntity toEntity(CreateOrderRequest orderRequest);

    OrderDTO toDto(OrderEntity order);

    OrderContract toContract(OrderEntity entity);

    OrderContract toContract(OrderDTO entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderEntity partialUpdate(OrderDTO orderDTO, @MappingTarget OrderEntity order);
}