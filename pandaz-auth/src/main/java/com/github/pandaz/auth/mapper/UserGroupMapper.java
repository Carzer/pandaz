package com.github.pandaz.auth.mapper;

import com.github.pandaz.auth.entity.UserGroupEntity;
import com.github.pandaz.commons.mapper.BasisMapper;

import java.util.List;

/**
 * 用户-组关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserGroupMapper extends BasisMapper<UserGroupEntity> {
    /**
     * 逻辑删除
     *
     * @param userGroupEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByUserCode(UserGroupEntity userGroupEntity);

    /**
     * 逻辑删除
     *
     * @param userGroupEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByGroupCode(UserGroupEntity userGroupEntity);

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

    /**
     * 批量插入
     *
     * @param list 成员关系
     * @return 执行结果
     */
    int batchInsert(List<UserGroupEntity> list);
}