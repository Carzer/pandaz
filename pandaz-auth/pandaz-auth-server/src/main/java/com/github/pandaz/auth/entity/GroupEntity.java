package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组
 *
 * @author Carzer
 * @since 2019-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_group")
public class GroupEntity extends BaseEntity {

    private static final long serialVersionUID = -1911290286990476968L;

    /**
     * 组名
     */
    @TableField("name")
    private String name;

    /**
     * 组编码
     */
    @TableField("code")
    private String code;

    /**
     * 父级组编码
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