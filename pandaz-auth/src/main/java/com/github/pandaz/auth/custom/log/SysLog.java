package com.github.pandaz.auth.custom.log;

import java.lang.annotation.*;

/**
 * 定义注解
 * {@link SysLogAspect}
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysLog {

    // 用户id字段
    String user();

    // 描述
    String description();

    // 是否从返回值中获取内容
    int getFromReturning() default 0;
}