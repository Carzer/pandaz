package com.pandaz.auth.custom.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 定义日志切入类
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Aspect
@Component
@Order(-5)
@Slf4j
public class SysLogAspect {

    /**
     * 定义切入点拦截规则，拦截ImosLog注解的方法
     */
    @Pointcut("@annotation(com.pandaz.auth.custom.log.SysLog)")
    public void logPointCut() {
        // 标记方法
    }

    /**
     * 记录操作日志
     */
    @AfterReturning(value = "logPointCut()", returning = "object")
    public void recordLog(JoinPoint joinPoint, Object object) {
        log.debug("target:{},obj:{}", joinPoint.getTarget(), object);
    }


    /**
     * 异常处理
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error(String.format("target:%s throws exception", joinPoint.getTarget()), e);
    }
}
