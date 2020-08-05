package com.github.pandaz.file.util;

import lombok.Getter;

/**
 * 文件类型枚举类
 *
 * @author Carzer
 * @since 2020-07-24
 */
@Getter
public enum FileTypeEnum {

    /**
     * 主mongodb
     */
    MONGO_PRIMARY(1, "mongo_primary"),
    /**
     * 从mongodb
     */
    MONGO_SECONDARY(2, "mongo_secondary");

    /**
     * key
     */
    private final int key;

    /**
     * value
     */
    private final String value;

    /**
     * 构造方法
     *
     * @param key   key
     * @param value value
     */
    FileTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
