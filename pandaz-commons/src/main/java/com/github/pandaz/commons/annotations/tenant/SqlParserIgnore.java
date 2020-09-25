package com.github.pandaz.commons.annotations.tenant;

import java.lang.annotation.*;

/**
 * mybatis plus的租户过滤
 * {@link com.github.pandaz.commons.aspects.tenant.SqlParserIgnoreAspect}
 *
 * @author Carzer
 * @since 2020-09-16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SqlParserIgnore {

    /**
     * 忽略范围
     */
    SqlParserIgnoreEnum[] ignoreScope() default {SqlParserIgnoreEnum.ALL};
}
