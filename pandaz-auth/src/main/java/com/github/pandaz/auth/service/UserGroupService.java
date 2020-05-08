package com.github.pandaz.auth.service;

import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.auth.entity.GroupEntity;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserGroupEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户-组服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface UserGroupService extends BaseService<UserGroupEntity> {

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return 执行结果
     */
    List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup);

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    int deleteByUserCode(UserEntity userEntity);

    /**
     * 根据用户组编码删除
     *
     * @param groupEntity groupEntity
     * @return int
     */
    int deleteByGroupCode(GroupEntity groupEntity);

    /**
     * 绑定用户组成员
     *
     * @param operator        操作者
     * @param currentDate     操作时间
     * @param userGroupEntity 用户信息
     * @return 执行结果
     */
    int bindGroupMember(String operator, LocalDateTime currentDate, UserGroupEntity userGroupEntity);

    /**
     * 绑定用户与用户组关系
     *
     * @param operator        操作者
     * @param currentDate     操作时间
     * @param userGroupEntity 用户信息
     * @return 执行结果
     */
    int bindUserGroup(String operator, LocalDateTime currentDate, UserGroupEntity userGroupEntity);

    /**
     * 列出组内成员
     *
     * @param userGroupEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindGroupMembers(UserGroupEntity userGroupEntity);

    /**
     * 列出用户所有的组
     *
     * @param userGroupEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindUserGroups(UserGroupEntity userGroupEntity);
}
