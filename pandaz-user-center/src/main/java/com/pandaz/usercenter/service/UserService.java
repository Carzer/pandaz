package com.pandaz.usercenter.service;

import com.github.pagehelper.Page;
import com.pandaz.usercenter.entity.UserEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 用户信息相关服务
 *
 * @author Carzer
 * @date 2019-07-16 14:30
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     * @author Carzer
     * @date 2019-07-16 14:53
     */
    UserEntity loadUserByUsername(String username);

    /**
     * 根据ID查找用户信息
     *
     * @param code code
     * @return com.pandaz.usercenter.entity.UserEntity
     * @author Carzer
     * @date 2019/10/28 16:40
     */
    UserEntity findByCode(String code);

    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     * @author Carzer
     * @date 2019/10/28 17:29
     */
    UserEntity updateByCode(UserEntity user);

    /**
     * 插入用户信息
     *
     * @param user 用户
     * @return UserEntity
     * @author Carzer
     * @date 2019/10/25 10:24
     */
    UserEntity insert(UserEntity user);

    /**
     * 删除用户信息
     *
     * @param userCode userCode
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:47
     */
    int deleteByCode(String userCode);

    /**
     * 获取用户信息页
     *
     * @param user 查询条件
     * @return com.github.pagehelper.Page<com.pandaz.usercenter.entity.UserEntity>
     * @author Carzer
     * @date 2019/10/28 13:54
     */
    Page<UserEntity> getPage(UserEntity user);
}
