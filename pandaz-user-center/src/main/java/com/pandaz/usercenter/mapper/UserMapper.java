package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 用户信息mapper
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 根据主键更新
     *
     * @param user 用户信息
     * @return int
     * @author Carzer
     * @date 2019-08-22 13:21
     */
    int updateByPrimaryKeySelective(UserEntity user);

    /**
     * 根据主键更新
     *
     * @param user 用户信息
     * @return int
     * @author Carzer
     * @date 2019-08-22 13:22
     */
    int updateByPrimaryKey(UserEntity user);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     * @author Carzer
     * @date 2019-08-22 13:19
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据编码删除
     *
     * @param code code
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:44
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 根据用户名获取用户信息
     *
     * @param loginName 用户名
     * @return com.pandaz.usercenter.entity.UserEntity
     * @author Carzer
     * @date 2019/10/23 15:19
     */
    UserEntity findByLoginName(@Value("loginName") String loginName);

    /**
     * 查询用户列表
     *
     * @param user 查询条件
     * @return java.util.List<com.pandaz.usercenter.entity.UserEntity>
     * @author Carzer
     * @date 2019/10/28 13:56
     */
    List<UserEntity> findList(UserEntity user);

    /**
     * 根据ID查找用户信息
     *
     * @param id id
     * @return com.pandaz.usercenter.entity.UserEntity
     * @author Carzer
     * @date 2019/10/28 16:41
     */
    UserEntity findById(@Value("id") String id);


    /**
     * 根据用户编码更新用户信息
     *
     * @param user user
     * @return int
     * @author Carzer
     * @date 2019/10/28 17:29
     */
    int updateByCode(UserEntity user);
}