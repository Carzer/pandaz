package com.pandaz.commons.dto.usercenter;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 授权菜单简化DTO
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
public class AuthMenuDTO implements Serializable {

    private static final long serialVersionUID = -7644611126544236407L;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单编码
     */
    @NotEmpty
    private String code;

    /**
     * 父菜单编码
     */
    private String parentCode;

    /**
     * url
     */
    private String url;

    /**
     * router
     */
    private String router;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sorting;

    /**
     * 位运算结果
     */
    private Integer bitResult;
}
