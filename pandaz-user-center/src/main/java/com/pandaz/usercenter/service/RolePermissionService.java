package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.RolePermissionEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 角色-权限服务
 *
 * @author Carzer
 * Date: 2019-11-06 10:16
 */
public interface RolePermissionService {

    /**
     * 插入方法
     *
     * @param rolePermission	rolePermission
     * @return int
     * @author Carzer
     * Date: 2019/11/6 10:19
     */
    int insert(RolePermissionEntity rolePermission);
}
