package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.PermissionEntity;

/**
 * 权限mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 插入方法
     *
     * @param permission permission
     * @return 插入结果
     */
    int insertSelective(PermissionEntity permission);
}