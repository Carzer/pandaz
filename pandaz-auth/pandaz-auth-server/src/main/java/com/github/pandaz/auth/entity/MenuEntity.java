package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单信息
 *
 * @author Carzer
 * @since 2019-11-01 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_menu")
public class MenuEntity extends BaseEntity {

    private static final long serialVersionUID = 6931559535632842272L;

    /**
     * 菜单名
     */
    @TableField("name")
    private String name;

    /**
     * 菜单编码
     */
    @TableField("code")
    private String code;

    /**
     * 系统编码
     */
    @TableField("os_code")
    private String osCode;

    /**
     * 父菜单编码
     */
    @TableField("parent_code")
    private String parentCode;

    /**
     * url
     */
    @TableField("url")
    private String url;

    /**
     * router
     */
    @TableField("router")
    private String router;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sorting")
    private Integer sorting;

    /**
     * 是否叶子节点
     */
    @TableField("is_leaf_node")
    private Byte isLeafNode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    @TableField("locked")
    private Byte locked;

    /**
     * 描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<MenuEntity> children;

    /**
     * 位运算结果
     */
    @TableField(exist = false)
    private List<Integer> bitResults;

    /**
     * 位运算结果
     */
    @TableField(exist = false)
    private Integer bitResult;
}