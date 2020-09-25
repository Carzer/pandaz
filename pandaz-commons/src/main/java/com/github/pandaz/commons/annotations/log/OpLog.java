package com.github.pandaz.commons.annotations.log;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 定义注解
 * {@link com.github.pandaz.commons.aspects.log.OpLogAspect}
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OpLog {

    @AliasFor("title")
    String value() default "";

    /**
     * 标题
     */
    @AliasFor("value")
    String title() default "";

    /**
     * 描述
     */
    String description() default "";

    /**
     * 用户信息所在的参数位置
     */
    int userIndex();
}