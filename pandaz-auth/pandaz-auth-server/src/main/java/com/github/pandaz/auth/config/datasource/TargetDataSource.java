package com.github.pandaz.auth.config.datasource;

import java.lang.annotation.*;

/**
 * 目标数据源注解，注解在方法上指定数据源的名称
 *
 * @author Carzer
 * @see DataSourceAspect
 * @since 2019-12-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TargetDataSource {

    /**
     * 数据源的名称
     */
    String value();
}