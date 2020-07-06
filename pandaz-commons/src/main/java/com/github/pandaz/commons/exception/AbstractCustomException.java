package com.github.pandaz.commons.exception;

import com.github.pandaz.commons.code.ICode;

/**
 * 自定义异常
 *
 * @author Carzer
 * @since 2020-07-06
 */
public abstract class AbstractCustomException extends RuntimeException {

    /**
     * 获取异常编码
     *
     * @return 异常编码
     */
    public abstract ICode getCode();
}
