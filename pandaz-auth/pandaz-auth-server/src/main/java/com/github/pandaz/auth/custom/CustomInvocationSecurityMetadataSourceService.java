package com.github.pandaz.auth.custom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SecurityMetadataSource
 *
 * @author Carzer
 * @since 2020-04-22
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

    /**
     * 权限信息Provider
     */
    private final MetadataResourceProvider metadataResourceProvider;

    /**
     * 判定用户请求的url 是否在权限表中
     * 如果在权限表中，则返回给 {@link CustomAccessDecisionManager#decide(Authentication, Object, Collection)} 方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * @param object 包含客户端发起的请求的request信息
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        Set<ConfigAttribute> configAttributes;
        // 查找匹配的权限信息
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Set<String> roleSet = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            // 如果开启超级管理员，并拥有符合的角色，则通过所有请求
            if (customProperties.getSuperAdmin().isEnable() && roleSet.contains(customProperties.getSuperAdmin().getName())) {
                return Collections.emptyList();
            }
            configAttributes = metadataResourceProvider.getResourceDefineValue(roleSet);
            // 添加过滤排除URL
            String[] excludedPaths = customProperties.getExcludedPaths();
            if (excludedPaths != null && excludedPaths.length > 0) {
                for (String excludedPath : excludedPaths) {
                    configAttributes.add(new SecurityConfig(excludedPath));
                }
            }
        } catch (Exception e) {
            log.error("获取url权限异常：", e);
            throw new IllegalArgumentException();
        }
        // 如果未配置过权限，则直接返回无权限
        if (CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException("no privilege");
        }
        return configAttributes;
    }

    /**
     * {@link SecurityMetadataSource#getAllConfigAttributes()}
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>(0);
    }

    /**
     * {@link SecurityMetadataSource#supports(Class)}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}