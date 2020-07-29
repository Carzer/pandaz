package com.github.pandaz.commons.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 已授权菜单
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@ApiModel(value = "AuthMenuDTO", description = "已授权菜单")
public class AuthMenuDTO implements Serializable {

    private static final long serialVersionUID = -7644611126544236407L;

    /**
     * 菜单名
     */
    @ApiModelProperty("菜单名")
    private String name;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    @NotEmpty
    private String code;

    /**
     * 父菜单编码
     */
    @ApiModelProperty("父菜单编码")
    private String parentCode;

    /**
     * url
     */
    @ApiModelProperty("后端请求url")
    private String url;

    /**
     * router
     */
    @ApiModelProperty("前端router")
    private String router;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sorting;

    /**
     * 位运算结果
     */
    @ApiModelProperty("位运算结果")
    private Integer bitResult;
}
