package com.deliver.order.mapper.contract;

import com.deliver.order.domain.OrderEntity;
import com.deliver.order.dto.OrderDTO;
import org.deliver.order.OrderContract;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderContractMapper {
    OrderContract toOrderContract(OrderEntity entity);

    OrderContract toOrderContract(OrderDTO entity);
}
