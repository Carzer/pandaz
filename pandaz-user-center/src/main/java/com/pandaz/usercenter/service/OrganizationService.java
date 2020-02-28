package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.OrganizationEntity;

/**
 * 组织信息服务
 *
 * @author Carzer
 * @since 2019/12/13
 */
public interface OrganizationService extends IService<OrganizationEntity> {

    /**
     * 根据编码查询
     *
     * @param code 组织编码
     * @return 组织信息
     */
    OrganizationEntity findByCode(String code);

    /**
     * 分页查询
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    IPage<OrganizationEntity> getPage(OrganizationEntity organizationEntity);

    /**
     * 插入方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    int insert(OrganizationEntity organizationEntity);

    /**
     * 更新方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    int updateByCode(OrganizationEntity organizationEntity);

    /**
     * 删除方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    int deleteByCode(OrganizationEntity organizationEntity);
}
