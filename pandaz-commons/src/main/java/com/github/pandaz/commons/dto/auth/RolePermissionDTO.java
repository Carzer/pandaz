package com.github.pandaz.commons.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限
 *
 * @author Carzer
 * @since 2020-04-10
 */
@Data
@ApiModel(value = "RolePermissionDTO", description = "角色-权限")
public class RolePermissionDTO implements Serializable {

    private static final long serialVersionUID = -1914596370817673983L;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 系统编码
     */
    @ApiModelProperty("系统编码")
    private String osCode;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String menuCode;

    /**
     * 权限
     */
    @ApiModelProperty("权限")
    private List<String> permissionCodes;
}
