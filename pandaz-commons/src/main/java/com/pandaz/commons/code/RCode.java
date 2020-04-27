package com.pandaz.commons.code;

import lombok.Getter;

/**
 * 通用编码
 *
 * @author Carzer
 * @since 2020-04-26
 */
@Getter
public enum RCode implements ICode {

    /**
     * 操作码
     */
    SUCCESS(1000, "操作成功。"),
    FAILED(1001, "操作失败。"),
    VALIDATE_FAILED(1002, "参数校验失败。"),
    TOKEN_EXPIRED(1003, "token已过期。"),
    UNAUTHORIZED(1004, "未授权。"),
    FORBIDDEN(1005, "权限不足，请联系管理员!"),
    BAD_CREDENTIALS(1006, "用户名或密码错误!"),
    ERROR(5000, "未知错误。");

    /**
     * 编码
     */
    private final long code;

    /**
     * 信息
     */
    private final String message;

    RCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}