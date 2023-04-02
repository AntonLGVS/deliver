package org.deliver.tracker;

import lombok.Data;

import java.util.UUID;

@Data
public class TrackerStatusContract {
    public enum Event {
        LOST, DELIVERED, RETURNED
    }

    private Event event;

    private UUID orderId;
}
