package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.RolePermissionEntity;

/**
 * 角色-权限mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RolePermissionMapper extends UcBaseMapper<RolePermissionEntity> {

    /**
     * 根据角色编码删除
     *
     * @param rolePermissionEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByRoleCode(RolePermissionEntity rolePermissionEntity);

    /**
     * 根据权限编码删除
     *
     * @param rolePermissionEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByPermissionCode(RolePermissionEntity rolePermissionEntity);
}