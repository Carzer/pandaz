package com.github.pandaz.auth.custom.constants;

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
     * 状态值
     */
    ACTIVE("0", "未过期"),
    EXPIRED("1", "已过期");

    /**
     * 值
     */
    private final String val;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 构造方法
     *
     * @param val  val
     * @param desc desc
     */
    ExpireStateEnum(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }
}
