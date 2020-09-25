package com.github.pandaz.auth.service;

import com.github.pandaz.auth.dto.SecurityUser;
import com.github.pandaz.auth.entity.RoleDetailEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.commons.service.BaseService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * 角色服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
public interface RoleService extends BaseService<RoleEntity> {

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return 执行结果
     */
    List<RoleDetailEntity> findByUserCode(String userCode, Byte isPrivate);

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode userCode用户编码
     * @return 执行结果
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
     * 获取所有角色编码
     *
     * @return 所有角色编码
     */
    List<String> listAllRoleCode();
}
