package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.OsInfoEntity;

/**
 * 系统信息服务
 *
 * @author Carzer
 * @since 2019-11-01 15:04
 */
public interface OsInfoService extends IService<OsInfoEntity> {

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return com.pandaz.usercenter.entity.OsInfoEntity
     */
    OsInfoEntity insert(OsInfoEntity osInfo);
}
