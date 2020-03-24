package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
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

    /**
     * 根据角色编码删除
     *
     * @param roleEntity 关系
     * @return 执行结果
     */
    int deleteByRoleCode(RoleEntity roleEntity);

    /**
     * 根据权限编码删除
     *
     * @param permissionEntity 关系
     * @return 执行结果
     */
    int deleteByPermissionCode(PermissionEntity permissionEntity);
}
