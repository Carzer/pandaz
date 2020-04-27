package com.pandaz.commons.code;

import lombok.Getter;

/**
 * 业务码
 *
 * @author Carzer
 * @since 2020-04-26
 */
@Getter
public enum BizCode implements ICode {

    /**
     * 操作码
     */
    VALID_CODE_EMPTY(10001, "验证码为空"),
    VALID_CODE_EXPIRED(10002, "验证码过期"),
    VALID_CODE_ERROR(10003, "验证码错误"),
    AUTH_TYPE_NOT_SUPPORT(10005, "不支持的授权方式")
    ;

    /**
     * 编码
     */
    private final long code;

    /**
     * 信息
     */
    private final String message;

    BizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
