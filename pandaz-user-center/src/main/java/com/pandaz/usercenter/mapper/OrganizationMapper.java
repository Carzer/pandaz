package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description: 组织信息mapper
 *
 * @author carzer
 * @date 2019/12/13
 */
@Repository
@Mapper
public interface OrganizationMapper {

    /**
     * 插入方法
     *
     * @param organization 组织信息
     * @return 插入结果
     */
    int insert(OrganizationEntity organization);

    /**
     * 插入方法
     *
     * @param organization 组织信息
     * @return 插入结果
     */
    int insertSelective(OrganizationEntity organization);
}