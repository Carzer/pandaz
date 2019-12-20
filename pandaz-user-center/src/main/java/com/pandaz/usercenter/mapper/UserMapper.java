package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 用户信息mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据主键更新
     *
     * @param user 用户信息
     * @return int
     */
    int updateByPrimaryKeySelective(UserEntity user);

    /**
     * 根据主键更新
     *
     * @param user 用户信息
     * @return int
     */
    int updateByPrimaryKey(UserEntity user);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据编码删除
     *
     * @param code code
     * @return int
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 根据用户名获取用户信息
     *
     * @param loginName 用户名
     * @return com.pandaz.usercenter.entity.UserEntity
     */
    UserEntity findByLoginName(@Value("loginName") String loginName);

    /**
     * 查询用户列表
     *
     * @param user 查询条件
     * @return java.util.List<com.pandaz.usercenter.entity.UserEntity>
     */
    List<UserEntity> findList(UserEntity user);

    /**
     * 根据ID查找用户信息
     *
     * @param id id
     * @return com.pandaz.usercenter.entity.UserEntity
     */
    UserEntity findById(@Value("id") String id);


    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     */
    int updateByCode(UserEntity user);

    /**
     * 插入方法
     *
     * @param user user
     * @return 插入结果
     */
    int insertSelective(UserEntity user);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return T
     */
    UserEntity findByCode(@Value("code") String code);
}