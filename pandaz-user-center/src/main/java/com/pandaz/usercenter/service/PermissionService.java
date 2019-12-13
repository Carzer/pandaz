package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.PermissionEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 权限相关服务
 *
 * @author Carzer
 * @date 2019-11-05 16:02
 */
public interface PermissionService {

    /**
     * 插入方法
     *
     * @param permission permission
     * @return com.pandaz.usercenter.entity.PermissionEntity
     * @author Carzer
     * @date 2019/11/5 16:07
     */
    PermissionEntity insert(PermissionEntity permission);
}
