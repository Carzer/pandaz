package com.github.pandaz.auth.service;

import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.auth.entity.RolePermissionEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色-权限服务
 *
 * @author Carzer
 * @since 2019-11-06
 */
public interface RolePermissionService extends BaseService<RolePermissionEntity> {

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
    List<String> listBindCodes(RolePermissionEntity rolePermissionEntity);

    /**
     * 根据系统编码查询
     *
     * @param osCode 系统编码
     * @return 权限列表
     */
    List<RolePermissionEntity> listByOsCode(String osCode);
}
