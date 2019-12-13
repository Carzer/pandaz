package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 组
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupEntity extends BaseEntity<GroupEntity> {

    private static final long serialVersionUID = -1911290286990476968L;
    /**
     * 组名
     */
    private String name;

    /**
     * 组编码
     */
    private String code;

    /**
     * 父级组编码
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