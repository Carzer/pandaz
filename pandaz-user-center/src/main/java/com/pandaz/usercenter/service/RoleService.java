package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;

import java.util.List;

/**
 * 角色服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 插入角色信息
     *
     * @param role 角色信息
     * @return int
     */
    int insert(RoleEntity role);

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     */
    List<RoleDetailEntity> findByUserCode(String userCode, Byte isPrivate);

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode userCode用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     */
    List<RoleDetailEntity> findByUserCode(String userCode);

    /**
     * 根据角色编码删除信息
     *
     * @param roleCode roleCode
     * @return int
     */
    int deleteByCode(String roleCode);
}
