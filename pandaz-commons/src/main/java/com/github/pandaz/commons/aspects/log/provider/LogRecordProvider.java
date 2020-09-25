package com.github.pandaz.commons.aspects.log.provider;

import org.aspectj.lang.JoinPoint;

/**
 * 日志记录
 *
 * @author Carzer
 * @since 2020-09-25
 */
@FunctionalInterface
public interface LogRecordProvider {

    /**
     * 记录日志
     *
     * @param joinPoint 切入点
     * @param object    信息
     * @param cause     异常
     */
    void record(JoinPoint joinPoint, Object object, Throwable cause);
}
