package com.pandaz.commons.dto.usercenter;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色-权限传输
 *
 * @author Carzer
 * @since 2020-04-10
 */
@Data
public class RolePermissionDTO implements Serializable {

    private static final long serialVersionUID = -1914596370817673983L;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 权限
     */
    private List<SimplePermissionDTO> permissions;

}
