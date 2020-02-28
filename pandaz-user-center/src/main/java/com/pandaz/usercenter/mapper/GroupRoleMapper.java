package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.GroupRoleEntity;

/**
 * 组-角色关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface GroupRoleMapper extends BaseMapper<GroupRoleEntity> {

    /**
     * 插入方法
     *
     * @param groupRole groupRole
     * @return 插入结果
     */
    int insertSelective(GroupRoleEntity groupRole);
}