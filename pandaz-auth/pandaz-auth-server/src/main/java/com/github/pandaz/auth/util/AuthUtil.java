package com.github.pandaz.auth.util;

import com.github.pandaz.commons.SecurityUser;
import com.github.pandaz.commons.dto.auth.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限工具类
 *
 * @author Carzer
 * @since 2019-09-03 14:37
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Component
@Slf4j
public final class AuthUtil {

    /**
     * 私有构造方法
     */
    private AuthUtil() {
    }

    /**
     * 由security上下文环境中获取角色列表
     *
     * @return 当前用户角色列表
     */
    public static Set<String> getRolesFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().parallelStream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    /**
     * 获取用户信息
     *
     * @param authentication authentication
     * @return 用户信息
     */
    public static UserDTO getUserFromOAuth2(OAuth2Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return ((SecurityUser) (authentication.getUserAuthentication()).getPrincipal()).getUser();
    }
}
