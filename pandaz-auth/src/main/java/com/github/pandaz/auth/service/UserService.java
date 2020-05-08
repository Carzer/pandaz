package com.github.pandaz.auth.service;

import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.auth.entity.UserEntity;

/**
 * 用户信息相关服务
 *
 * @author Carzer
 * @since 2019-07-16 14:30
 */
public interface UserService extends BaseService<UserEntity> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserEntity loadUserByUsername(String username);
}
