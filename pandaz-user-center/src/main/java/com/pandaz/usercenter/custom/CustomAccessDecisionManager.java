package com.pandaz.usercenter.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AccessDecisionManager
 *
 * @author Carzer
 * @since 2020-04-22
 */
@Component
@Slf4j
public class CustomAccessDecisionManager implements AccessDecisionManager {

    /**
     * 是否启用超级管理员角色
     */
    @Value("${custom.super-admin.enable}")
    private boolean enableSuperAdmin;

    /**
     * 超级管理员角色名称
     */
    @Value("${custom.super-admin.name}")
    private String superAdminName;

    /**
     * 判定是否拥有权限
     * 直接return即代表通过
     *
     * @param authentication   UserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * @param o                包含客户端发起的请求的requset信息
     * @param configAttributes {@link CustomInvocationSecurityMetadataSourceService#getAttributes(Object)}返回的结果
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) {
        // 如果开启超级管理员，则通过所有请求
        if (enableSuperAdmin) {
            List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roleList.contains(superAdminName)) {
                return;
            }
        }
        // 如果请求的资源为系统中配置的资源，进行角色匹配
        if (!CollectionUtils.isEmpty(configAttributes)) {
            List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            for (ConfigAttribute configAttribute : configAttributes) {
                if (roleList.contains(configAttribute.getAttribute())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no privilege");
    }

    /**
     * {@link AccessDecisionManager#supports(ConfigAttribute)}
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * {@link AccessDecisionManager#supports(Class)}
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}