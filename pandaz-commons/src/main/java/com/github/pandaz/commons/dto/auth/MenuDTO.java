package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    @NotEmpty
    @Size(min = 1, max = 50)
    private String code;

    /**
     * 系统编码
     */
    @ApiModelProperty("系统编码")
    @Size(min = 1, max = 50)
    private String osCode;

    /**
     * 父菜单编码
     */
    @ApiModelProperty("父菜单编码")
    @Size(min = 1, max = 50)
    private String parentCode;

    /**
     * url
     */
    @ApiModelProperty("后端请求url")
    @Size(min = 1, max = 500)
    private String url;

    /**
     * router
     */
    @ApiModelProperty("前端router")
    @Size(min = 1, max = 200)
    private String router;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    @Size(min = 1, max = 200)
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
    @Min(0)
    @Max(1)
    private Byte isLeafNode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @ApiModelProperty("是否锁定(0:未锁定，1:已锁定)")
    @Min(0)
    @Max(1)
    private Byte locked;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @Size(min = 1, max = 256)
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
