package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.entity.UserGroupEntity;
import com.pandaz.usercenter.mapper.UserGroupMapper;
import com.pandaz.usercenter.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-组服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupEntity> implements UserGroupService {

    /**
     * 用户-组mapper
     */
    private final UserGroupMapper userGroupMapper;

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     */
    @Override
    public List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup) {
        QueryWrapper<UserGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code", userGroup.getUserCode());
        return userGroupMapper.selectList(queryWrapper);
    }

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    @Override
    public int deleteByUserCode(UserEntity userEntity) {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setUserCode(userEntity.getCode());
        userGroupEntity.setDeletedBy(userEntity.getDeletedBy());
        userGroupEntity.setDeletedDate(userEntity.getDeletedDate());
        return userGroupMapper.logicDeleteByUserCode(userGroupEntity);
    }

    /**
     * 根据用户组编码删除
     *
     * @param groupEntity groupEntity
     * @return int
     */
    @Override
    public int deleteByGroupCode(GroupEntity groupEntity) {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setGroupCode(groupEntity.getCode());
        userGroupEntity.setDeletedBy(groupEntity.getDeletedBy());
        userGroupEntity.setDeletedDate(groupEntity.getDeletedDate());
        return userGroupMapper.logicDeleteByGroupCode(userGroupEntity);
    }

    /**
     * 插入方法
     *
     * @param userGroup userGroup
     */
    @Override
    public int insert(UserGroupEntity userGroup) {
        return userGroupMapper.insertSelective(userGroup);
    }
}
