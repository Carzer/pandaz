package com.github.pandaz.auth.custom.handler;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.exception.BizException;
import com.github.pandaz.commons.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局exception捕捉
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    /**
     * 异常捕获：自定义业务异常
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public R<String> executeFailed(BizException e) {
        log.error("业务异常：{}", e.getBizCode().getMessage());
        return new R<>(e.getBizCode());
    }

    /**
     * 异常捕获：未授权
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public R<String> authFailed(AuthenticationException e) {
        log.warn("未授权：{}", e.getMessage());
        return new R<>(RCode.UNAUTHORIZED);
    }

    /**
     * 异常捕获：权限拒绝
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public R<String> authFailed(AccessDeniedException e) {
        log.warn("权限拒绝：{}", e.getMessage());
        return new R<>(RCode.FORBIDDEN);
    }

    /**
     * 异常捕获：请求异常
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public R<String> validFailed(IllegalArgumentException e) {
        log.warn("请求异常：{}", e.getMessage());
        return R.fail(e.getMessage());
    }

    /**
     * 异常捕获：运行时异常
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public R<String> executeFailed(RuntimeException e) {
        log.error("运行时异常：", e);
        return R.fail(e.getMessage());
    }
}
