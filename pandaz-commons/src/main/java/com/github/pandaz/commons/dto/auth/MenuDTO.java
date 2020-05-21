package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 菜单信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuDTO", description = "菜单信息")
public class MenuDTO extends BaseDTO {

    private static final long serialVersionUID = -3846122439228839344L;

    /**
     * 菜单名
     */
    @ApiModelProperty("菜单名")
    private String name;

    /**
     * 菜单编码
     */
    @NotEmpty
    @ApiModelProperty("菜单编码")
    private String code;

    /**
     * 系统编码
     */
    @ApiModelProperty("系统编码")
    private String osCode;

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
     * 是否叶子节点
     */
    @ApiModelProperty("是否叶子节点")
    private Byte isLeafNode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    private Byte locked;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String remark;

    /**
     * 子菜单
     */
    @ApiModelProperty("子菜单")
    private List<MenuDTO> children;

    /**
     * 位运算结果
     */
    @ApiModelProperty("位运算结果")
    private Integer bitResult;
}
