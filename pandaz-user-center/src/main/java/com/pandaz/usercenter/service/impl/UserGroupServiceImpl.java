package com.pandaz.usercenter.service.impl;

import com.pandaz.usercenter.entity.UserGroupEntity;
import com.pandaz.usercenter.mapper.UserGroupMapper;
import com.pandaz.usercenter.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 用户-组服务
 *
 * @author Carzer
 * @date 2019-11-05 17:27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupServiceImpl implements UserGroupService {

    /**
     * 用户-组mapper
     */
    private final UserGroupMapper userGroupMapper;

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     * @author Carzer
     * @date 2019/11/5 17:26
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
     * @author Carzer
     * @date 2019/11/5 17:26
     */
    @Override
    public int deleteByUserCode(String userCode) {
        return userGroupMapper.deleteByUserCode(userCode);
    }

    /**
     * 插入方法
     *
     * @param userGroup userGroup
     * @author Carzer
     * @date 2019/11/5 17:31
     */
    @Override
    public int insert(UserGroupEntity userGroup) {
        return userGroupMapper.insertSelective(userGroup);
    }
}
