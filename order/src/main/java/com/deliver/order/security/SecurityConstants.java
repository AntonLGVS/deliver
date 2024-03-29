package com.deliver.order.security;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
    public static final String CHECK_PERMISSION = "@security.hasPermission(returnObject)";
    public static final String IS_ADMIN = "hasRole('ADMIN')";

    public static final String IS_COURIER = "hasRole('COURIER')";
    public static final String NOT_COURIER = "!" + IS_COURIER;
}
