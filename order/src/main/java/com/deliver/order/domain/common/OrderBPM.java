package com.deliver.order.domain.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderBPM {
    public enum State {
        NEW, IN_PROGRESS, DELIVERED, CANCELLED
    }

    public enum Action {
        CREATE, EDIT, ASSIGN, DELIVER, LOST, CANCEL
    }
}
