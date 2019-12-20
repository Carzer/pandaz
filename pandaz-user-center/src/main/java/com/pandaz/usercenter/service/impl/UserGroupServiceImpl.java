package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return userGroupMapper.findByUserCode(userGroup);
    }

    /**
     * 根据用户编码删除
     *
     * @param userCode userCode
     * @return int
     */
    @Override
    public int deleteByUserCode(String userCode) {
        return userGroupMapper.deleteByUserCode(userCode);
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
