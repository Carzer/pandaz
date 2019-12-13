package com.pandaz.usercenter.config.datasource;

import java.lang.annotation.*;

/**
 * Description: 目标数据源注解，注解在方法上指定数据源的名称
 *
 * @author carzer
 * @date 2019/12/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TargetDataSource {
    String value();//数据源的名称
}