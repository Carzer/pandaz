package com.pandaz.usercenter.custom.constants;

import lombok.Getter;

/**
 * 通用URL
 *
 * @author Carzer
 * @since 2020-04-23
 */
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
     * url
     */
    @Getter
    private final String url;

    /**
     * name
     */
    @Getter
    private final String name;

    /**
     * 构造方法
     *
     * @param url  url
     * @param name name
     */
    UrlEnum(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
