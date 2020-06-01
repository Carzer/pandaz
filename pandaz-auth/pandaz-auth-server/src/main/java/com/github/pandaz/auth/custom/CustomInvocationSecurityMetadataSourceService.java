package com.github.pandaz.auth.custom;

import com.github.pandaz.auth.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
     * 判定用户请求的url 是否在权限表中
     * 如果在权限表中，则返回给 {@link CustomAccessDecisionManager#decide(Authentication, Object, Collection)} 方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * @param object 包含客户端发起的请求的requset信息
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        Collection<ConfigAttribute> configAttributes;
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        // 如果开启超级管理员，并拥有符合的角色，则通过所有请求
        if (customProperties.getSuperAdmin().isEnable()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roleList = authentication.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority().toLowerCase())
                    .collect(Collectors.toList());
            if (roleList.contains(customProperties.getSuperAdmin().getName().toLowerCase())) {
                return Collections.emptyList();
            }
        }
        // 过滤排除URL
        String[] excludedPaths = customProperties.getExcludedPaths();
        for (String excludedPath : excludedPaths) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(excludedPath);
            if (matcher.matches(request)) {
                return Collections.emptyList();
            }
        }
        // 查找匹配的权限信息
        try {
            configAttributes = AuthUtil.getResourceDefineValue(request.getRequestURI());
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