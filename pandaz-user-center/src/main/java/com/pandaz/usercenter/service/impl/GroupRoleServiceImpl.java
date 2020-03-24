package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.mapper.GroupRoleMapper;
import com.pandaz.usercenter.service.GroupRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组-角色服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRoleEntity> implements GroupRoleService {

    /**
     * 组-角色mapper
     */
    private final GroupRoleMapper groupRoleMapper;

    /**
     * 插入方法
     *
     * @param groupRole groupRole
     * @return int
     */
    @Override
    public int insert(GroupRoleEntity groupRole) {
        return groupRoleMapper.insertSelective(groupRole);
    }

    /**
     * 根据组编码查询
     *
     * @param groupRole groupRole
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     */
    @Override
    public List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole) {
        QueryWrapper<GroupRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_code", groupRole.getGroupCode());
        return groupRoleMapper.selectList(queryWrapper);
    }

    /**
     * 跟组编码删除
     *
     * @param groupEntity groupCode
     * @return int
     */
    @Override
    public int deleteByGroupCode(GroupEntity groupEntity) {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setGroupCode(groupEntity.getCode());
        groupRoleEntity.setDeletedBy(groupEntity.getDeletedBy());
        groupRoleEntity.setDeletedDate(groupEntity.getDeletedDate());
        return groupRoleMapper.logicDeleteByGroupCode(groupRoleEntity);
    }

    /**
     * 跟角色编码删除
     *
     * @param roleEntity 删除信息
     * @return int
     */
    @Override
    public int deleteByRoleCode(RoleEntity roleEntity) {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setRoleCode(roleEntity.getCode());
        groupRoleEntity.setDeletedBy(roleEntity.getDeletedBy());
        groupRoleEntity.setDeletedDate(roleEntity.getDeletedDate());
        return groupRoleMapper.logicDeleteByRoleCode(groupRoleEntity);
    }
}
