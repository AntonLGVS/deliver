package com.deliver.order.service;

import com.deliver.order.dto.OrderDTO;

import java.util.UUID;

public interface OrderService {
    OrderDTO getOrder(UUID uuid);
}
