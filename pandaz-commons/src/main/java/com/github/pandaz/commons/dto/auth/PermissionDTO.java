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
    @Size(min = 1, max = 100)
    private String name;

    /**
     * 权限编码
     */
    @ApiModelProperty("权限编码")
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
     * 系统名称
     */
    @ApiModelProperty("系统名称")
    @Size(min = 1, max = 100)
    private String osName;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    @Size(min = 1, max = 50)
    private String menuCode;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @Size(min = 1, max = 100)
    private String menuName;

    /**
     * 位移数
     */
    @ApiModelProperty("位移数")
    @Min(0)
    @Max(25)
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
