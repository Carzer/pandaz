package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.RolePermissionEntity;

/**
 * 角色-权限服务
 *
 * @author Carzer
 * @since 2019-11-06
 */
public interface RolePermissionService extends IService<RolePermissionEntity> {

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return int
     */
    int insert(RolePermissionEntity rolePermission);
}
