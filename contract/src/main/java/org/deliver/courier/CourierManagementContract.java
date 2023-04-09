package org.deliver.courier;

import lombok.Data;

import java.util.UUID;

@Data
public class CourierManagementContract {

    public enum Status {
        ACQUIRED, RELEASED
    }
    private UUID orderId;
    private String courierId;
    private Status status;
}
