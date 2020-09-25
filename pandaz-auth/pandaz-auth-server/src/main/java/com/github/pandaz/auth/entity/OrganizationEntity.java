package com.github.pandaz.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组织信息
 *
 * @author Carzer
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("auth_organization")
public class OrganizationEntity extends BaseEntity {

    private static final long serialVersionUID = 4704915823644957779L;

    /**
     * 组织名
     */
    @TableField("name")
    private String name;

    /**
     * 组织编码
     */
    @TableField("code")
    private String code;

    /**
     * 父组织编码
     */
    @TableField("parent_code")
    private String parentCode;

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
     * 数据权限级别
     */
    @TableField("level")
    private Integer level;

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
     * 子组织
     */
    @TableField(exist = false)
    private List<OrganizationEntity> children;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
}