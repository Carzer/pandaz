package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.GroupRoleEntity;

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

}