package com.pandaz.usercenter.custom.constants;

import lombok.Getter;

/**
 * 过期状态
 *
 * @author Carzer
 * @since 2020-03-18
 */
@Getter
public enum ExpireStateEnum {

    /**
     * 0未过期，1已过期
     */
    ACTIVE("0"), EXPIRED("1");

    /**
     * 值
     */
    private final String val;

    /**
     * 构造方法
     *
     * @param val val
     */
    ExpireStateEnum(String val) {
        this.val = val;
    }
}
