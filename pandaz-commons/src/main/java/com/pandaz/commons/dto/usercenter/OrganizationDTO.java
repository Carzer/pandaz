package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 组织信息DTO
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationDTO extends BaseDTO {

    private static final long serialVersionUID = -5865998917996643986L;

    /**
     * 主键
     */
    private String id;

    /**
     * 组织名
     */
    private String name;

    /**
     * 组织编码
     */
    @NotEmpty
    private String code;

    /**
     * 父组织编码
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
     * 级别
     */
    private Integer level;

    /**
     * 是否叶子节点
     */
    private Byte isLeafNode;

    /**
     * 是否锁定(0:未锁定，1:已锁定)
     */
    private Byte locked;
}
