package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 角色mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据用户编码获取非私有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     */
    List<RoleDetailEntity> getPublicRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取私有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     */
    List<RoleDetailEntity> getPrivateRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取所有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     */
    List<RoleDetailEntity> getAllRoles(@Value("userCode") String userCode);

    /**
     * 删除方法
     *
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param code code
     * @return int
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 更新方法
     *
     * @param role role
     * @return int
     */
    int updateByPrimaryKeySelective(RoleEntity role);

    /**
     * 更新方法
     *
     * @param role role
     * @return int
     */
    int updateByPrimaryKey(RoleEntity role);

    /**
     * 插入方法
     *
     * @param role role
     * @return 插入结果
     */
    int insertSelective(RoleEntity role);

}