package com.github.pandaz.auth.service;

import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.commons.service.BaseService;

import java.util.List;

/**
 * 组织信息服务
 *
 * @author Carzer
 * @since 2019/12/13
 */
public interface OrganizationService extends BaseService<OrganizationEntity> {

    /**
     * 获取所有组织
     *
     * @param organizationEntity organizationEntity
     * @return 执行结果
     */
    List<OrganizationEntity> getAll(OrganizationEntity organizationEntity);
}
