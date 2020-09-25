package com.github.pandaz.commons.aspects.tenant;

import cn.hutool.core.util.ArrayUtil;
import com.github.pandaz.commons.annotations.tenant.SqlParserIgnore;
import com.github.pandaz.commons.annotations.tenant.SqlParserIgnoreEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 租户sql过滤
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Aspect
@Component
@Order(-5)
@Slf4j
public class SqlParserIgnoreAspect {

    /**
     * 定义切入点拦截规则，拦截{@link SqlParserIgnore}注解的方法
     */
    @Pointcut("@annotation(com.github.pandaz.commons.annotations.tenant.SqlParserIgnore)")
    public void pointCut() {
        // 标记方法
    }

    /**
     * 在方法执行前记录忽略范围
     *
     * @param joinPoint 切入点
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        String threadName = Thread.currentThread().getName();
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取具体内容
            SqlParserIgnore sqlParserIgnore = method.getAnnotation(SqlParserIgnore.class);
            SqlParserIgnoreEnum[] ignoreScope = sqlParserIgnore.ignoreScope();
            int scope = 0;
            if (ArrayUtil.isEmpty(ignoreScope)) {
                scope = SqlParserIgnoreEnum.ALL.getValue();
            } else {
                for (SqlParserIgnoreEnum anEnum : ignoreScope) {
                    scope |= anEnum.getValue();
                }
            }
            SqlParserIgnoreHolder.setIgnoreScope(scope);
            log.debug("Thread: {}, add ignoreScope:[{}] to thread-local success", threadName, ignoreScope);
        } catch (Exception e) {
            log.error(String.format("Thread: %s, add ignoreScope to thread-local error :", threadName), e);
        }
    }

    /**
     * 执行完切面后，将线程共享中的忽略列表清空
     *
     * @param joinPoint 切入点
     */
    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        String threadName = Thread.currentThread().getName();
        SqlParserIgnoreHolder.removeIgnoreScope();
        log.debug("Thread: {}, remove ignoreScope from thread-local success", threadName);
    }
}
