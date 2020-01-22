package com.pandaz.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限
 *
 * @author Carzer
 * @since 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sys_permission")
public class PermissionEntity extends BaseEntity {

    private static final long serialVersionUID = 2322340301939133014L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 权限名
     */
    @TableField("name")
    private String name;

    /**
     * 权限编码
     */
    @TableField("code")
    private String code;

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
     * 资源URL
     */
    @TableField("url")
    private String url;

    /**
     * 请求类型get\post\put\delete
     * {@link com.pandaz.usercenter.custom.constants.PermissionConstants}
     */
    @TableField("request_type")
    private Byte requestType;

    /**
     * 优先级及位移数
     */
    @TableField("priority")
    private Byte priority;

    /**
     * 位运算结果
     */
    @TableField("bit_result")
    private Integer bitResult;

    /**
     * 权限级别，只有组织级别小于等于权限级别，才可使用该权限
     */
    private Integer level;

}