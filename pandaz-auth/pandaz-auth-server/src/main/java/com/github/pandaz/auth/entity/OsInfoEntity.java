package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统信息
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_os_info")
public class OsInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1210965567272983588L;

    /**
     * 系统名
     */
    @TableField("name")
    private String name;

    /**
     * 系统编码
     */
    @TableField("code")
    private String code;

    /**
     * 父系统编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;
}