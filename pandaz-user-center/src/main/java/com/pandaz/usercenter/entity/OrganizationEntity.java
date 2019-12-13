package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: 组织信息
 *
 * @author carzer
 * @date 2019/12/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationEntity extends BaseEntity<OrganizationEntity> {

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织编码
     */
    private String code;

    /**
     * 父级编码
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
     * 组织级别
     */
    private Integer level;

    /**
     * 是否叶子节点
     */
    private Byte isLeafNode;

}