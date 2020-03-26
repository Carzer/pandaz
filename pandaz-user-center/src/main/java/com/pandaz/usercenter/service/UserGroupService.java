package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.entity.UserGroupEntity;

import java.util.List;

/**
 * 用户-组服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface UserGroupService extends UcBaseService<UserGroupEntity> {

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     */
    List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup);

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    int deleteByUserCode(UserEntity userEntity);

    /**
     * 根据用户组编码删除
     *
     * @param groupEntity groupEntity
     * @return int
     */
    int deleteByGroupCode(GroupEntity groupEntity);

}
