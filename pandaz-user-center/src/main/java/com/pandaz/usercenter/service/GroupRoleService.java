package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.GroupRoleEntity;

import java.util.List;

/**
 * 组-角色服务
 *
 * @author Carzer
 * @since 2019-11-05 17:33
 */
public interface GroupRoleService extends IService<GroupRoleEntity> {

    /**
     * 插入方法
     *
     * @param groupRole groupRole
     * @return int
     */
    int insert(GroupRoleEntity groupRole);

    /**
     * 根据组编码查询
     *
     * @param groupRole groupRole
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     */
    List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole);

    /**
     * 跟组编码删除
     *
     * @param groupCode groupCode
     * @return int
     */
    int deleteByGroupCode(String groupCode);
}
