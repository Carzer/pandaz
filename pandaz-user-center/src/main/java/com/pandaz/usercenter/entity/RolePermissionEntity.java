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

    @TableField("permission_code")
    private String permissionCode;

    @TableField(exist = false)
    private Integer version;
}