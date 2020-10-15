package com.github.pandaz.commons.annotations.security;

import java.lang.annotation.*;

/**
 * 替换自定义权限表达式中的{}
 *
 * @author Carzer
 * @since 2020-10-15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface PreAuth {

    /**
     * 替换自定义权限表达式中的{}
     */
    String value() default "";
}
