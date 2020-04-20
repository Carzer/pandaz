package com.pandaz.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 主键
     */
    @TableId("id")
    private String id;

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

    @TableField(exist = false)
    private Integer version;

}