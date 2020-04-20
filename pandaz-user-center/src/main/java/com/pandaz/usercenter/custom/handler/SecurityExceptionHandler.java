package com.pandaz.usercenter.custom.handler;

import com.pandaz.commons.util.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局exception捕捉
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Slf4j
@ControllerAdvice
@ResponseBody
@Component
public class SecurityExceptionHandler {

    /**
     * 异常捕获
     *
     * @param e 异常
     * @return 执行结果
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    public ExecuteResult<String> authFailed(AuthenticationException e) {
        ExecuteResult<String> result = new ExecuteResult<>();
        String msg = e.getMessage();
        log.warn("错误信息：{}", msg);
        result.setError(msg);
        return result;
    }

}
