package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.mapper.RolePermissionMapper;
import com.pandaz.usercenter.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色-权限服务
 *
 * @author Carzer
 * @since 2019-11-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {

    /**
     * 角色-权限mapper
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return int
     */
    @Override
    public int insert(RolePermissionEntity rolePermission) {
        return rolePermissionMapper.insertSelective(rolePermission);
    }

    /**
     * 根据角色编码删除
     *
     * @param roleEntity 关系
     * @return 执行结果
     */
    @Override
    public int deleteByRoleCode(RoleEntity roleEntity) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleCode(roleEntity.getCode());
        rolePermissionEntity.setDeletedBy(roleEntity.getDeletedBy());
        rolePermissionEntity.setDeletedDate(roleEntity.getDeletedDate());
        return rolePermissionMapper.logicDeleteByRoleCode(rolePermissionEntity);
    }

    /**
     * 根据权限编码删除
     *
     * @param permissionEntity 关系
     * @return 执行结果
     */
    @Override
    public int deleteByPermissionCode(PermissionEntity permissionEntity) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermissionCode(permissionEntity.getCode());
        rolePermissionEntity.setDeletedBy(permissionEntity.getDeletedBy());
        rolePermissionEntity.setDeletedDate(permissionEntity.getDeletedDate());
        return rolePermissionMapper.logicDeleteByPermissionCode(rolePermissionEntity);
    }
}
