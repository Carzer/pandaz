package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 菜单信息
 *
 * @author Carzer
 * Date: 2019-11-01 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuEntity extends BaseEntity<MenuEntity> {

    private static final long serialVersionUID = 6931559535632842272L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 菜单父级编码
     */
    private String parentCode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sorting;

    /**
     * 是否叶子节点
     */
    private Byte isLeafNode;

    /**
     * 是否锁定
     */
    private Byte locked;

    /**
     * 子菜单
     */
    private List<MenuEntity> children;

}