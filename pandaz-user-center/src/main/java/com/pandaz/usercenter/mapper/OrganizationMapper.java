package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.OrganizationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Description: 组织信息mapper
 *
 * @author carzer
 * @date 2019/12/13
 */
@Mapper
@Repository
public interface OrganizationMapper extends BaseMapper<OrganizationEntity> {

}