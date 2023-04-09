package com.deliver.order.dto;

import com.deliver.order.domain.common.OrderBPM;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDTO extends TrackedDTO {
    private UUID id;
    private OrderBPM.State state;
    private String targetAddress;
    private String comment;
    private String courier;
}
