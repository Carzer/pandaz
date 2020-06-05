package com.github.pandaz.auth.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * 自定义权限认证翻译器
 *
 * @author Carzer
 * @since 2019-11-04
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * 通用配置
     */
    private CustomProperties.SuperAdmin superAdmin;

    @Autowired
    public void setSuperAdmin(CustomProperties customProperties) {
        this.superAdmin = customProperties.getSuperAdmin();
    }

    /**
     * 判断是否有权限
     *
     * @param authentication authentication
     * @param prefix         prefix
     * @param permission     permission
     * @return boolean
     */
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object prefix, Object permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 如果开启超级管理员，并拥有符合的角色，则通过所有请求
        if (superAdmin.isEnable()) {
            return authorities.parallelStream().anyMatch(
                    authority -> authority.getAuthority().equals(superAdmin.getName()));
        }
        return false;
    }

    /**
     * 判断是否有权限
     *
     * @param authentication authentication
     * @param targetId       targetId
     * @param targetType     targetType
     * @param permission     permission
     * @return boolean
     */
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        return false;
    }
}