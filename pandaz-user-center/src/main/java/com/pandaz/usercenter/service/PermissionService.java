package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.PermissionEntity;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface PermissionService extends IService<PermissionEntity> {

    /**
     * 插入方法
     *
     * @param permission permission
     * @return com.pandaz.usercenter.entity.PermissionEntity
     */
    PermissionEntity insert(PermissionEntity permission);
}
