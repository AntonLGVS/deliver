package com.deliver.order.dto;

import io.swagger.v3.oas.annotations.Parameter;

public record CreateOrderRequest(
        @Parameter(description = "Address") String targetAddress,
        @Parameter(description = "Comment for order") String comment) {
}
