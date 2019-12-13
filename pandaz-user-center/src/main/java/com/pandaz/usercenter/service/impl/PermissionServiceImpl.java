package com.pandaz.usercenter.service.impl;

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
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 权限相关服务
 *
 * @author Carzer
 * @date 2019-11-05 16:07
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PermissionServiceImpl implements PermissionService {

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
     * @author Carzer
     * @date 2019/11/5 16:07
     */
    @Override
    public PermissionEntity insert(PermissionEntity permission) {
        checkUtils.checkOrSetCode(permission, permissionMapper, "权限编码已存在", null, null);
        permission.setId(UuidUtil.getUnsignedUuid());
        permissionMapper.insertSelective(permission);
        return permission;
    }
}
