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
    TEST(10000, "测试用");

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
