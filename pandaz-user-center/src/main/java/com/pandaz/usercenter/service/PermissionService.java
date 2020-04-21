package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.entity.PermissionEntity;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface PermissionService extends UcBaseService<PermissionEntity> {

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 查询结果
     */
    PermissionEntity findByCode(String code);

    /**
     * 分页方法
     *
     * @param permissionEntity 权限信息
     * @return 分页结果
     */
    IPage<PermissionEntity> getPage(PermissionEntity permissionEntity);

    /**
     * 更新方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    int updateByCode(PermissionEntity permissionEntity);

    /**
     * 删除方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    int deleteByCode(PermissionEntity permissionEntity);

    /**
     * 根据菜单编码删除
     *
     * @param permissionEntity 编码信息
     * @return 执行结果
     */
    int deleteByMenuCode(PermissionEntity permissionEntity);
}
