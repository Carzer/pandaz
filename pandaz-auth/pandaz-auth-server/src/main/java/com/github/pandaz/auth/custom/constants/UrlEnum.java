package com.github.pandaz.auth.custom.constants;

import lombok.Getter;

/**
 * 通用URL
 *
 * @author Carzer
 * @since 2020-04-23
 */
@Getter
public enum UrlEnum {

    /**
     * 通用URL
     */
    GET("/get", "查看"),
    PAGE("/getPage", "分页"),
    INSERT("/insert", "新增"),
    UPDATE("/update", "编辑"),
    DELETE("/delete", "删除"),
    IMPORT("/import", "导入"),
    EXPORT("/export", "导出");

    /**
     * key
     */
    private final String key;

    /**
     * desc
     */
    private final String desc;

    /**
     * 构造方法
     *
     * @param key  key
     * @param desc desc
     */
    UrlEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
