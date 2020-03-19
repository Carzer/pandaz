package com.pandaz.usercenter.custom.constants;

import lombok.Getter;

/**
 * 过期状态
 *
 * @author Carzer
 * @since 2020-03-18
 */
public enum ExpireStateEnum {

    /**
     * 0未过期，1已过期
     */
    active("0"), expired("1");

    /**
     * 值
     */
    @Getter
    private String val;

    /**
     * 构造方法
     *
     * @param val val
     */
    ExpireStateEnum(String val) {
        this.val = val;
    }
}
