package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.SecurityUser;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * 角色服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
public interface RoleService extends UcBaseService<RoleEntity> {

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
     * 根据安全用户类查询
     *
     * @param securityUser 安全用户类
     * @return 角色列表
     */
    Set<GrantedAuthority> findBySecurityUser(SecurityUser securityUser);

    /**
     * 根据角色编码删除信息
     *
     * @param roleEntity 角色编码
     * @return int
     */
    int deleteByCode(RoleEntity roleEntity);

    /**
     * 根据编码查询
     *
     * @param code 角色编码
     * @return 角色信息
     */
    RoleEntity findByCode(String code);

    /**
     * 分页查询
     *
     * @param roleEntity 角色信息
     * @return 分页结果
     */
    IPage<RoleEntity> getPage(RoleEntity roleEntity);

    /**
     * 更新方法
     *
     * @param roleEntity 角色信息
     * @return 执行结果
     */
    int updateByCode(RoleEntity roleEntity);
}
