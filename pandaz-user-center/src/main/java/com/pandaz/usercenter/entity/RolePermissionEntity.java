package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 角色-权限
 *
 * @author Carzer
 * Date: 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermissionEntity extends BaseEntity<RolePermissionEntity> {

    private static final long serialVersionUID = -837121706345482664L;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 系统编码
     */
    private String osCode;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 请求类型get\post\put\delete
     */
    private Byte requestType;

    /**
     * 权限值
     */
    private Integer permissionValue;
}