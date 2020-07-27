package com.github.pandaz.redis.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源AOP切面定义
 *
 * @author Carzer
 * @see TargetRedisTemplate
 * @see RedisSwitchHolder
 * @since 2019-12-13
 */
@Component
@Aspect
@Slf4j
public class RedisTemplateAspect {

    /**
     * AOP切面的切入点（使用注解的方法）
     */
    @Pointcut("@annotation(com.github.pandaz.redis.config.redis.TargetRedisTemplate)")
    public void redisTemplatePointCut() {
        // 标记方法
    }

    /**
     * 在方法执行前切换数据源
     *
     * @param joinPoint 切入点
     */
    @Before("redisTemplatePointCut()")
    public void before(JoinPoint joinPoint) {
        String threadName = Thread.currentThread().getName();
        try {
            // 如果方法上存在切换数据源的注解，则根据注解内容进行数据源切换
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取具体内容
            TargetRedisTemplate targetRedisTemplate = method.getAnnotation(TargetRedisTemplate.class);
            String targetName = targetRedisTemplate.value();
            RedisSwitchHolder.setTargetName(targetName);
            log.debug("Thread: {}, add targetRedisTemplateName:[{}] to thread-local success", threadName, targetName);
        } catch (Exception e) {
            log.error(String.format("Thread: %s, add targetRedisTemplateName to thread-local error :", threadName), e);
        }
    }

    /**
     * 执行完切面后，将线程共享中的数据源名称清空
     */
    @After("redisTemplatePointCut()")
    public void after() {
        String threadName = Thread.currentThread().getName();
        String targetName = RedisSwitchHolder.getTargetName();
        RedisSwitchHolder.remove();
        log.debug("Thread: {}, remove targetRedisTemplateName:[{}] from thread-local success", threadName, targetName);
    }
}