package com.github.pandaz.auth.service;

import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.commons.service.BaseService;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface PermissionService extends BaseService<PermissionEntity> {

    /**
     * 根据菜单编码删除
     *
     * @param permissionEntity 编码信息
     * @return 执行结果
     */
    int deleteByMenuCode(PermissionEntity permissionEntity);
}
