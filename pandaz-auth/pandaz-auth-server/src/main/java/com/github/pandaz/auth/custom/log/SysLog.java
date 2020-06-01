package com.github.pandaz.auth.custom.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 定义注解
 * {@link SysLogAspect}
 * 使用方法参照{@link com.github.pandaz.auth.controller.UserController#getPage}
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysLog {

    @AliasFor("title")
    String value() default "";

    // 标题
    @AliasFor("value")
    String title() default "";

    // 描述
    String description() default "";

    // Principal所在的参数位置
    int userIndex();
}