package com.pandaz.usercenter.entity;

import com.pandaz.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 用户-组关系
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroupEntity extends BaseEntity<UserGroupEntity> {

    private static final long serialVersionUID = 6268647528238681429L;
    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 组编码
     */
    private String groupCode;

    /**
     * 是否私有
     */
    private Byte isPrivate;
}