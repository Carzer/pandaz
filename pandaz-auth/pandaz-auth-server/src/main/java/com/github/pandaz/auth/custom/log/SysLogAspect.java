package com.github.pandaz.auth.custom.log;

import com.github.pandaz.commons.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 定义日志处理类
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
     * 定义切入点拦截规则，拦截{@link SysLog}注解的方法
     */
    @Pointcut("@annotation(com.github.pandaz.auth.custom.log.SysLog)")
    public void logPointCut() {
        // 标记方法
    }

    /**
     * 记录操作日志
     */
    @AfterReturning(value = "logPointCut()", returning = "object")
    public void recordLog(JoinPoint joinPoint, Object object) {
        recordLog(joinPoint, object, null);
    }

    /**
     * 异常处理
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        recordLog(joinPoint, null, e);
    }

    /**
     * 记录日志
     *
     * @param joinPoint 切入点
     * @param object    信息
     * @param cause     异常
     */
    private void recordLog(JoinPoint joinPoint, Object object, Throwable cause) {
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            // 获取操作
            SysLog sysLog = method.getAnnotation(SysLog.class);
            // 操作标题
            String title = sysLog.title();
            // 详细描述
            String description = sysLog.description();
            // 用户ID字段
            int userIndex = sysLog.userIndex();
            String userName = ((Principal) joinPoint.getArgs()[userIndex]).getName();
            // 获取方法执行前时间
            LocalDateTime date = LocalDateTime.now();
            // 获取请求的ip
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String ip = IpUtil.getIpAddress(request);
            String url = request.getRequestURI();
            Object result;
            if (cause != null) {
                result = cause.getMessage();
            } else {
                result = object;
            }
            log.debug("username:{},ip:{},url:{},className:{},methodName:{},title:{},description:{},operationDate:{},result:{}",
                    userName, ip, url, className, methodName, title, description, date, result);
        } catch (Exception e) {
            log.error("获取操作日志异常：{}", e.getMessage());
        }
    }
}
