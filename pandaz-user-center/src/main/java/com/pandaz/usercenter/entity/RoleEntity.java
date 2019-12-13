package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 角色
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEntity extends BaseEntity<RoleEntity> {

    private static final long serialVersionUID = 5634197313784403857L;
    /**
     * 角色名
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 父编码
     */
    private String parentCode;

    /**
     * 是否私有
     */
    private Byte isPrivate;

    /**
     * 是否锁定
     */
    private Byte locked;

}