package com.pandaz.usercenter.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pandaz:com.pandaz.usercenter.entity
 * <p>
 * 角色的详细信息
 *
 * @author Carzer
 * @date 2019-10-25 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDetailEntity extends RoleEntity {

    private static final long serialVersionUID = -1269563521724576083L;
    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 组编码
     */
    private String groupCode;

}
