package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.mapper.PermissionMapper;
import com.pandaz.usercenter.service.PermissionService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    /**
     * 权限mapper
     */
    private final PermissionMapper permissionMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtils<PermissionEntity, PermissionMapper> checkUtils;

    /**
     * 插入方法
     *
     * @param permission permission
     * @return com.pandaz.usercenter.entity.PermissionEntity
     */
    @Override
    public PermissionEntity insert(PermissionEntity permission) {
        checkUtils.checkOrSetCode(permission, permissionMapper, "权限编码已存在", null, null);
        permission.setId(UuidUtil.getUnsignedUuid());
        permissionMapper.insertSelective(permission);
        return permission;
    }
}
