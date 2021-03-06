package io.anymobi.common.annotation;

import io.anymobi.services.mybatis.member.EventHoldingValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PublishEvent {

    Class<? extends EventHoldingValue> eventType();

    String params() default "";
}
