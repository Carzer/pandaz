package com.github.pandaz.auth.mapper;

import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.commons.mapper.BasisMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserMapper extends BasisMapper<UserEntity> {

    /**
     * 根据登录名查询
     *
     * @param loginName 登录名
     * @return 用户
     */
    @Select("select * from auth_user where login_name = #{loginName}")
    UserEntity loadUserByUsername(@Param("loginName") String loginName);
}