package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息相关服务
 *
 * @author Carzer
 * @since 2019-07-16 14:30
 */
public interface UserService extends IService<UserEntity> {

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
     * 插入用户信息
     *
     * @param user 用户
     * @return UserEntity
     */
    UserEntity insert(UserEntity user);

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

    /**
     * 批量删除用户
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes);
}
