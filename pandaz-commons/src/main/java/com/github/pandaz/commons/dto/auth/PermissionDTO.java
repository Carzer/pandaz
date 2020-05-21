package com.github.pandaz.commons.dto.auth;

import com.github.pandaz.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 权限信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "PermissionDTO", description = "权限信息")
public class PermissionDTO extends BaseDTO {

    private static final long serialVersionUID = -6253234950648940203L;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String name;

    /**
     * 权限编码
     */
    @NotEmpty
    @ApiModelProperty("权限编码")
    private String code;

    /**
     * 系统编码
     */
    @ApiModelProperty("系统编码")
    private String osCode;

    /**
     * 系统名称
     */
    @ApiModelProperty("系统名称")
    private String osName;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String menuCode;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 位移数
     */
    @ApiModelProperty("位移数")
    private Byte bitDigit;

    /**
     * 位运算结果
     */
    @ApiModelProperty("位运算结果")
    private Integer bitResult;

    /**
     * 权限级别，只有组织级别小于等于权限级别，才可使用该权限
     */
    @ApiModelProperty("权限级别，只有组织级别小于等于权限级别，才可使用该权限")
    private Integer level;
}
