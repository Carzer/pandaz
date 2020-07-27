package com.github.pandaz.redis.config.redis;

import java.lang.annotation.*;

/**
 * 目标数据源注解，注解在方法上指定数据源的名称
 *
 * @author Carzer
 * @see RedisTemplateAspect
 * @since 2019-12-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TargetRedisTemplate {
    // 数据源的名称
    String value();
}