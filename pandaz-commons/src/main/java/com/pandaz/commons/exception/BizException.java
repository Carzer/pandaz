package com.pandaz.commons.exception;

import com.pandaz.commons.code.BizCode;
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
    private final BizCode bizCode;

    /**
     * 构造方法
     *
     * @param bizCode 业务码
     */
    public BizException(BizCode bizCode) {
        this.bizCode = bizCode;
    }
}
