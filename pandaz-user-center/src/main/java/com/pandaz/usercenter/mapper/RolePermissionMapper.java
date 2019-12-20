package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.RolePermissionEntity;

/**
 * 角色-权限mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return 插入结果
     */
    int insertSelective(RolePermissionEntity rolePermission);
}