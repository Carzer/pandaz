package com.github.pandaz.auth.mapper;

import com.github.pandaz.auth.entity.RolePermissionEntity;
import com.github.pandaz.commons.mapper.BasisMapper;

import java.util.List;

/**
 * 角色-权限mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RolePermissionMapper extends BasisMapper<RolePermissionEntity> {

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

    /**
     * 批量插入
     *
     * @param list 权限关系
     * @return 执行结果
     */
    int batchInsert(List<RolePermissionEntity> list);

    /**
     * 查询已绑定的权限编码
     *
     * @param rolePermissionEntity 插叙条件
     * @return 权限编码
     */
    List<String> listBindCodes(RolePermissionEntity rolePermissionEntity);

    /**
     * 根据系统编码查询权限表
     *
     * @param osCode 系统编码
     * @return 权限表
     */
    List<RolePermissionEntity> listByOsCode(String osCode);

    /**
     * 根据系统编码和角色编码查询
     *
     * @param rolePermissionEntity 查询条件
     * @return 执行结果
     */
    List<RolePermissionEntity> listByOsCodeAndRoleCode(RolePermissionEntity rolePermissionEntity);
}