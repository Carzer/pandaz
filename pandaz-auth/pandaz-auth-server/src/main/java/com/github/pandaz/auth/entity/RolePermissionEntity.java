package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色-权限
 *
 * @author Carzer
 * @since 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_role_permission")
public class RolePermissionEntity extends BaseEntity {

    private static final long serialVersionUID = -837121706345482664L;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 系统编码
     */
    @TableField("os_code")
    private String osCode;

    /**
     * 菜单编码
     */
    @TableField("menu_code")
    private String menuCode;

    /**
     * 权限编码
     */
    @TableField(exist = false)
    private List<String> permissionCodes;

    /**
     * 版本
     */
    @TableField(exist = false)
    private Integer version;
}