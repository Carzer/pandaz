package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组-角色关系
 *
 * @author Carzer
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_group_role")
public class GroupRoleEntity extends BaseEntity {

    private static final long serialVersionUID = -8221295574031161475L;

    /**
     * 组编码
     */
    @TableField("group_code")
    private String groupCode;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 是否私有(0:否，1:是)
     */
    @TableField("is_private")
    private Byte isPrivate;

    /**
     * 版本
     */
    @TableField(exist = false)
    private Integer version;

    /**
     * 组编码
     */
    @TableField(exist = false)
    private List<String> groupCodes;

    /**
     * 角色编码
     */
    @TableField(exist = false)
    private List<String> roleCodes;
}