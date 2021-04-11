package com.spw.authmg.configuration.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 数据源注解
 * @Scope  class & method
 * @Author spw
 * @Date 2021/03/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface DataSource {
    String value() default "master";
}
