package com.deliver.order.statemachine.cqrs;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CtxAttributes {
    public static final String REQUEST = "ATTRIBUTE_REQUEST";
    public static final String PAYLOAD_ID = "ATTRIBUTE_PAYLOAD_ID";
    public static final String PAYLOAD = "ATTRIBUTE_PAYLOAD";
    public static final String RESULT = "ATTRIBUTE_RESULT";
}
