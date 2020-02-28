package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
}
