package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 角色服务
 *
 * @author Carzer
 * @date 2019-10-25 11:20
 */
public interface RoleService {

    /**
     * 插入角色信息
     *
     * @param role 角色信息
     * @return int
     * @author Carzer
     * @date 2019/10/25 11:30
     */
    int insert(RoleEntity role);

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * @date 2019/10/25 11:22
     */
    List<RoleDetailEntity> findByUserCode(String userCode, Byte isPrivate);

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode userCode用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * @date 2019/10/25 11:22
     */
    List<RoleDetailEntity> findByUserCode(String userCode);

    /**
     * 根据角色编码删除信息
     *
     * @param roleCode roleCode
     * @return int
     * @author Carzer
     * @date 2019/10/25 16:20
     */
    int deleteByCode(String roleCode);
}
