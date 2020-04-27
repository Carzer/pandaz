package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.RoleEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组-角色服务
 *
 * @author Carzer
 * @since 2019-11-05 17:33
 */
public interface GroupRoleService extends UcBaseService<GroupRoleEntity> {

    /**
     * 根据组编码查询
     *
     * @param groupRole groupRole
     * @return 执行结果
     */
    List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole);

    /**
     * 跟组编码删除
     *
     * @param groupEntity groupCode
     * @return int
     */
    int deleteByGroupCode(GroupEntity groupEntity);

    /**
     * 跟角色编码删除
     *
     * @param roleEntity 删除信息
     * @return int
     */
    int deleteByRoleCode(RoleEntity roleEntity);

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
     * 绑定组-角色关系
     *
     * @param operator        操作者
     * @param currentDate     当前时间
     * @param groupRoleEntity 组-角色关系
     * @return 执行结果
     */
    int bindGroupRole(String operator, LocalDateTime currentDate, GroupRoleEntity groupRoleEntity);

    /**
     * 绑定角色-组关系
     *
     * @param operator        操作者
     * @param currentDate     当前时间
     * @param groupRoleEntity 组-角色关系
     * @return 执行结果
     */
    int bindRoleGroup(String operator, LocalDateTime currentDate, GroupRoleEntity groupRoleEntity);
}
