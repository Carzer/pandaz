package com.pandaz.commons.dto.usercenter;

import com.pandaz.commons.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 菜单信息DTO
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends BaseDTO {

    private static final long serialVersionUID = -3846122439228839344L;

    /**
     * 主键
     */
    private String id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单编码
     */
    @NotEmpty
    private String code;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 父菜单编码
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
     * 是否锁定(0:未锁定，1:已锁定)
     */
    private Byte locked;

    /**
     * 子菜单
     */
    private List<MenuDTO> children;
}
