package com.github.pandaz.auth.service;

import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserOrgEntity;
import com.github.pandaz.commons.service.BaseService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户-组织
 *
 * @author Carzer
 * @since 2020-05-21
 */
public interface UserOrgService extends BaseService<UserOrgEntity> {

    /**
     * 根据用户编码查询
     *
     * @param userOrgEntity userOrgEntity
     * @return 执行结果
     */
    List<UserOrgEntity> findByUserCode(UserOrgEntity userOrgEntity);

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    int deleteByUserCode(UserEntity userEntity);

    /**
     * 根据组织编码删除
     *
     * @param organizationEntity organizationEntity
     * @return int
     */
    int deleteByOrgCode(OrganizationEntity organizationEntity);

    /**
     * 绑定组织成员
     *
     * @param operator      操作者
     * @param currentDate   操作时间
     * @param userOrgEntity 信息
     * @return 执行结果
     */
    int bindOrgMember(String operator, LocalDateTime currentDate, UserOrgEntity userOrgEntity);

    /**
     * 绑定用户与组织关系
     *
     * @param operator      操作者
     * @param currentDate   操作时间
     * @param userOrgEntity 信息
     * @return 执行结果
     */
    int bindUserOrg(String operator, LocalDateTime currentDate, UserOrgEntity userOrgEntity);

    /**
     * 列出组织内成员
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindOrgMembers(UserOrgEntity userOrgEntity);

    /**
     * 列出用户所有的组织
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindUserOrg(UserOrgEntity userOrgEntity);
}
