package com.github.pandaz.auth.util;

import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限工具类
 *
 * @author Carzer
 * @since 2019-09-03 14:37
 */
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
    public static R<List<String>> getRoleListFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roleList = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleList)) {
            return new R<>(RCode.ROLE_EMPTY);
        }
        return new R<>(roleList);
    }
}
