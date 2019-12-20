package com.pandaz.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色-权限
 *
 * @author Carzer
 * @since 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role_permission")
public class RolePermissionEntity extends BaseEntity {

    private static final long serialVersionUID = -837121706345482664L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

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
     * 请求方式
     */
    @TableField("request_type")
    private Byte requestType;

    /**
     * 权限值
     */
    @TableField("permission_value")
    private Integer permissionValue;
}