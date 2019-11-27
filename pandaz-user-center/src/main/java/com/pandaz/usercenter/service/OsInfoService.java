package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.OsInfoEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 系统信息服务
 *
 * @author Carzer
 * Date: 2019-11-01 15:04
 */
public interface OsInfoService {

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return com.pandaz.usercenter.entity.OsInfoEntity
     * @author Carzer
     * Date: 2019/11/1 15:10
     */
    OsInfoEntity insert(OsInfoEntity osInfo);
}
