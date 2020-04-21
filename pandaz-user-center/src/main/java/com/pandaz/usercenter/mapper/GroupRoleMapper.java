package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.GroupRoleEntity;

import java.util.List;

/**
 * 组-角色关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface GroupRoleMapper extends UcBaseMapper<GroupRoleEntity> {

    /**
     * 根据组编码删除
     *
     * @param groupRoleEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByGroupCode(GroupRoleEntity groupRoleEntity);

    /**
     * 根据角色编码删除
     *
     * @param groupRoleEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByRoleCode(GroupRoleEntity groupRoleEntity);

    /**
     * 列出绑定的角色
     *
     * @param groupRoleEntity 条件
     * @return 角色
     */
    List<String> listBindGroupRoles(GroupRoleEntity groupRoleEntity);

    /**
     * 列出绑定的组
     *
     * @param groupRoleEntity 条件
     * @return 组
     */
    List<String> listBindRoleGroups(GroupRoleEntity groupRoleEntity);

    /**
     * 批量插入
     *
     * @param list list
     * @return 执行结果
     */
    int batchInsert(List<GroupRoleEntity> list);
}