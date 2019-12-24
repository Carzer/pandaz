package com.pandaz.usercenter.custom.log;

import com.pandaz.commons.util.IpUtil;
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
import java.util.Date;

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
    @Pointcut("@annotation(com.pandaz.usercenter.custom.log.SysLog)")
    public void logPointCut() {
    }

    /**
     * 记录操作日志
     */
    @AfterReturning(value = "logPointCut()", returning = "object")
    public void recordLog(JoinPoint joinPoint, Object object) {
        record(joinPoint, object);
    }


    /**
     * 异常处理
     */
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        record(joinPoint, e);
    }

    /**
     * 获取关键字
     *
     * @return java.lang.String
     */
    private String getKeyFromParams(String key, String params) {
        StringBuilder result = new StringBuilder();
        try {

        } catch (Exception e) {
            log.warn("获取参数出错！");
        }
        return result.toString();
    }

    /**
     * 记录日志
     */
    private void record(JoinPoint joinPoint, Object returnObject, Throwable e) {
        //暂时不记录报错信息系
        if (e != null) {
            return;
        }
        try {
            //获取方法执行前时间
            Date date = new Date();
            //获取请求的ip
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = IpUtil.getIpAddress(request);
            String url = request.getRequestURI();
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            String userInfo = null;
            //获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求的方法名
            String methodName = method.getName();
            //获取操作
            SysLog log = method.getAnnotation(SysLog.class);
            //用户ID字段
            String userId = log.user();
            //是否从返回值中获取信息
            boolean fromReturning = Integer.valueOf(1).equals(log.getFromReturning());
            if (fromReturning) {

            } else {

            }
            //将参数所在的数组转换成json
            //获取操作描述
            String desc = log.description();
            //获取关键字
            String key = log.key();

        } catch (Exception ex) {
            log.warn("获取参数出错！", ex);
        }
    }

    /**
     * 记录日志
     *
     * @author Carzer
     * Date: 2019/11/12
     */
    private void record(JoinPoint joinPoint, Object returnObject) {
        record(joinPoint, returnObject, null);
    }

    /**
     * 记录日志
     *
     * @author Carzer
     * Date: 2019/11/12
     */
    private void record(JoinPoint joinPoint, Throwable e) {
        record(joinPoint, null, e);
    }

}
