package com.github.pandaz.auth.mapper;

import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.commons.mapper.BasisMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 组织信息mapper
 *
 * @author Carzer
 * @since 2019-12-13
 */
public interface OrganizationMapper extends BasisMapper<OrganizationEntity> {

    /**
     * 获取菜单树
     *
     * @param organizationEntity organizationEntity
     * @return 菜单树
     */
    List<OrganizationEntity> getAllAsTree(OrganizationEntity organizationEntity);

    /**
     * 根据父级编码查询
     *
     * @param parentCode 父级编码
     * @return 菜单列表
     */
    List<OrganizationEntity> selectByParentCode(@Value("parentCode") String parentCode);
}