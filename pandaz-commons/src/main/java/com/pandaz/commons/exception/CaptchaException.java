package com.pandaz.commons.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码exception
 *
 * @author Carzer
 * @since 2020-04-27
 */
public class CaptchaException extends AuthenticationException {

    /**
     * 构造方法
     *
     * @param msg 消息
     * @param e   e
     */
    public CaptchaException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * 构造方法
     *
     * @param msg 消息
     */
    public CaptchaException(String msg) {
        super(msg);
    }

    /**
     * 获取堆栈信息
     *
     * @return 堆栈信息
     */
    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }
}
