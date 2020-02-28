package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.UserEntity;

/**
 * 用户信息mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 插入方法
     *
     * @param user user
     * @return 插入结果
     */
    int insertSelective(UserEntity user);
}