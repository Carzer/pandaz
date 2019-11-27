package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 组-角色关系
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupRoleEntity extends BaseEntity<GroupRoleEntity> {

    private static final long serialVersionUID = -8221295574031161475L;
    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 是否私有
     */
    private Byte isPrivate;

}