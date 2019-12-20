package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.UserGroupEntity;

import java.util.List;

/**
 * 用户-组服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
public interface UserGroupService extends IService<UserGroupEntity> {

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
     * @param userCode userCode
     * @return int
     */
    int deleteByUserCode(String userCode);

    /**
     * 插入方法
     *
     * @param userGroup userGroup
     * @return int
     */
    int insert(UserGroupEntity userGroup);
}
