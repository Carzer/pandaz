package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.entity.UserEntity;

/**
 * 用户信息相关服务
 *
 * @author Carzer
 * @since 2019-07-16 14:30
 */
public interface UserService extends UcBaseService<UserEntity> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserEntity loadUserByUsername(String username);

    /**
     * 根据ID查找用户信息
     *
     * @param code code
     * @return com.pandaz.usercenter.entity.UserEntity
     */
    UserEntity findByCode(String code);

    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     */
    int updateByCode(UserEntity user);

    /**
     * 删除用户信息
     *
     * @param userEntity 用户信息
     * @return int
     */
    int deleteByCode(UserEntity userEntity);

    /**
     * 获取用户信息页
     *
     * @param userEntity 查询条件
     * @return 分页结果
     */
    IPage<UserEntity> getPage(UserEntity userEntity);

}
