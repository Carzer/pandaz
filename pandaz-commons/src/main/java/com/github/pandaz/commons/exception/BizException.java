package com.github.pandaz.commons.exception;

import com.github.pandaz.commons.code.BizCode;
import lombok.Getter;

/**
 * 业务异常类
 *
 * @author Carzer
 * @since 2020-04-26
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 业务码
     */
    private final BizCode code;

    /**
     * 构造方法
     *
     * @param code 业务码
     */
    public BizException(BizCode code) {
        this.code = code;
    }
}
