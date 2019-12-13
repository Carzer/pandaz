package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 权限
 *
 * @author Carzer
 * @date 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionEntity extends BaseEntity<PermissionEntity> {

    private static final long serialVersionUID = 2322340301939133014L;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型get\post\put\delete
     * {@link com.pandaz.usercenter.custom.constants.PermissionConstants}
     */
    private Byte requestType;

    /**
     * 优先级及位移数
     */
    private Byte priority;

    /**
     * 位运算结果
     */
    private Integer bitResult;

    /**
     * 权限级别，只有组织级别不大于权限级别，才可使用该权限
     * {@link OrganizationEntity#level}
     */
    private Integer level;

}