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
     * 根据编码删除
     *
     * @param code code
     * @return int
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 查询用户列表
     *
     * @param user 查询条件
     * @return java.util.List<com.pandaz.usercenter.entity.UserEntity>
     */
    List<UserEntity> findList(UserEntity user);

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