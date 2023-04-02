package com.deliver.order.statemachine.order.spi.annotation;


import com.deliver.order.domain.common.OrderBPM;
import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@OnTransition
public @interface OrderOnTransition {
    OrderBPM.State[] source() default {};

    OrderBPM.State[] target() default {};
}
