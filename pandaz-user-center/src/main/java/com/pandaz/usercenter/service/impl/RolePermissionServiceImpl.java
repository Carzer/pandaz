package com.pandaz.usercenter.service.impl;

import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.mapper.RolePermissionMapper;
import com.pandaz.usercenter.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 角色-权限服务
 *
 * @author Carzer
 * Date: 2019-11-06 10:19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePermissionServiceImpl implements RolePermissionService {

    /**
     * 角色-权限mapper
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return int
     * @author Carzer
     * Date: 2019/11/6 10:19
     */
    @Override
    public int insert(RolePermissionEntity rolePermission) {
        return rolePermissionMapper.insertSelective(rolePermission);
    }
}
