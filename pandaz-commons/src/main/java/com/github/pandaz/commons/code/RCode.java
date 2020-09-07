package com.github.pandaz.commons.code;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用编码
 *
 * @author Carzer
 * @since 2020-04-26
 */
public enum RCode implements ICode {

    /**
     * 操作码
     */
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "操作失败"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    VALID_CODE_EMPTY(1003, "验证码为空"),
    VALID_CODE_EXPIRED(1004, "验证码过期"),
    VALID_CODE_ERROR(1005, "验证码错误"),
    TOKEN_EXPIRED(1006, "token已过期"),
    UNAUTHORIZED(1007, "未授权"),
    FORBIDDEN(1008, "权限不足，请联系管理员!"),
    BAD_CREDENTIALS(1009, "用户名或密码错误!"),
    ROLE_EMPTY(1010, "当前用户没有配置角色，请联系管理员!"),
    ERROR(5000, "未知错误");

    /**
     * 编码
     */
    @Getter
    private final long code;

    /**
     * 信息
     */
    @Getter
    private final String message;

    /**
     * 所有枚举集合
     */
    private static final Map<Long, RCode> ENUMS = new HashMap<>();

    /*
     * 静态方法
     */
    static {
        for (RCode rCode : RCode.values()) {
            ENUMS.put(rCode.getCode(), rCode);
        }
    }

    /**
     * 构造方法
     *
     * @param code    编码
     * @param message 信息
     */
    RCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static RCode getEnum(Long code) {
        return ENUMS.get(code);
    }
}