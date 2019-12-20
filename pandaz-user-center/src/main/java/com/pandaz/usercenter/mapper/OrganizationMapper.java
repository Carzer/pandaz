package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.OrganizationEntity;

/**
 * 组织信息mapper
 *
 * @author Carzer
 * @since 2019-12-13
 */
public interface OrganizationMapper extends BaseMapper<OrganizationEntity> {

    /**
     * 插入方法
     *
     * @param organization organization
     * @return 插入结果
     */
    int insertSelective(OrganizationEntity organization);
}