package org.deliver.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderContract {

    public enum OrderState {
        NEW, PENDING, IN_PROGRESS, DELIVERED, CANCELLED, UNKNOWN
    }

    private UUID id;
    private OrderState state;
    private String createdBy;
    private String createdAt;
}
