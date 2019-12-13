package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.UserGroupEntity;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 用户-组服务
 *
 * @author Carzer
 * @date 2019-11-05 17:26
 */
public interface UserGroupService {

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return java.util.List<com.pandaz.usercenter.entity.UserGroupEntity>
     * @author Carzer
     * @date 2019/11/5 17:26
     */
    List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup);


    /**
     * 根据用户编码删除
     *
     * @param userCode userCode
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:26
     */
    int deleteByUserCode(String userCode);

    /**
     * 插入方法
     *
     * @param userGroup	userGroup
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:35
     */
    int insert(UserGroupEntity userGroup);
}
