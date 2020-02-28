package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 查询结果
     */
    OsInfoEntity findByCode(String code);

    /**
     * 分页方法
     *
     * @param osInfoEntity 系统信息
     * @return 分页结果
     */
    IPage<OsInfoEntity> getPage(OsInfoEntity osInfoEntity);

    /**
     * 更新方法
     *
     * @param osInfoEntity 系统信息
     * @return 执行结果
     */
    int updateByCode(OsInfoEntity osInfoEntity);

    /**
     * 删除方法
     *
     * @param osInfoEntity 系统信息
     * @return 执行结果
     */
    int deleteByCode(OsInfoEntity osInfoEntity);
}
