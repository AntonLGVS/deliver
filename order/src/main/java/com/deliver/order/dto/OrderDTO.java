package com.deliver.order.dto;

import com.deliver.order.bpm.OrderState;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDTO extends TrackedDTO {
    private UUID id;
    private OrderState state;
    private String targetAddress;
    private String comment;
}
