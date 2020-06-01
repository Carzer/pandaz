package com.github.pandaz.file.custom;

import com.github.pandaz.commons.exception.FileException;
import com.github.pandaz.commons.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ExceptionHandler(FileException.class)
    public R<String> executeFailed(FileException e) {
        log.error("业务异常：{}", e.getCode().getMessage());
        return new R<>(e.getCode());
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
