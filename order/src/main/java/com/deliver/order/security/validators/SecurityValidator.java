package com.deliver.order.security.validators;

public interface SecurityValidator<T> {

    boolean isSupport(Object object);

    boolean check(T object);
}
