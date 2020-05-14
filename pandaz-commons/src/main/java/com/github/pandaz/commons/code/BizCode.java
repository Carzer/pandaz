package com.github.pandaz.commons.code;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务编码
 *
 * @author Carzer
 * @since 2020-04-26
 */
public enum BizCode implements ICode {

    /**
     * 操作码
     */
    TEST(10001, "测试-占坑");

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
    private static final Map<Long, BizCode> ENUMS = new HashMap<>();

    /*
      静态方法
     */
    static {
        for (BizCode bizCode : BizCode.values()) {
            ENUMS.put(bizCode.getCode(), bizCode);
        }
    }

    /**
     * 构造方法
     *
     * @param code    编码
     * @param message 信息
     */
    BizCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据编码获取枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static BizCode getEnum(Long code) {
        return ENUMS.get(code);
    }
}
