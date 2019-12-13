package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.GroupRoleEntity;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 组-角色服务
 *
 * @author Carzer
 * @date 2019-11-05 17:33
 */
public interface GroupRoleService {

    /**
     * 插入方法
     *
     * @param groupRole	groupRole
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:36
     */
    int insert(GroupRoleEntity groupRole);

    /**
     * 根据组编码查询
     *
     * @param groupRole	groupRole
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     * @author Carzer
     * @date 2019/11/5 17:36
     */
    List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole);

    /**
     * 跟组编码删除
     *
     * @param groupCode	groupCode
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:37
     */
    int deleteByGroupCode(String groupCode);
}
