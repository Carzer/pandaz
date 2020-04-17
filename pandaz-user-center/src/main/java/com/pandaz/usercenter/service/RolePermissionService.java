package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色-权限服务
 *
 * @author Carzer
 * @since 2019-11-06
 */
public interface RolePermissionService extends UcBaseService<RolePermissionEntity> {

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

    /**
     * 绑定权限
     *
     * @param operator             操作人
     * @param currentDate          当前时间
     * @param rolePermissionEntity 权限信息
     * @return 执行结果
     */
    int bindPermissions(String operator, LocalDateTime currentDate, RolePermissionEntity rolePermissionEntity);

    /**
     * 查询已绑定的权限编码
     *
     * @param rolePermissionEntity 查询条件
     * @return 权限编码
     */
    List<String> listCodes(RolePermissionEntity rolePermissionEntity);
}
