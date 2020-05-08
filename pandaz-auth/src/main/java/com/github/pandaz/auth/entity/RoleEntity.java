package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author Carzer
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_role")
public class RoleEntity extends BaseEntity {

    private static final long serialVersionUID = 5634197313784403857L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 角色名
     */
    @TableField("name")
    private String name;

    /**
     * 角色编码
     */
    @TableField("code")
    private String code;

    /**
     * 父角色编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 是否私有(0:否，1:是)
     */
    @TableField("is_private")
    private Byte isPrivate;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;

}