package com.pandaz.auth.util;

import com.pandaz.commons.SecurityUser;
import com.pandaz.commons.code.RCode;
import com.pandaz.commons.dto.auth.UserDTO;
import com.pandaz.commons.util.R;
import com.pandaz.auth.entity.RolePermissionEntity;
import com.pandaz.auth.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
     * 静态权限服务
     */
    private static RolePermissionService staticRolePermissionService;

    /**
     * 权限表
     */
    private static ConcurrentHashMap<String, Collection<ConfigAttribute>> map;

    /**
     * 获取权限服务
     *
     * @return 权限服务
     */
    private static RolePermissionService getRolePermissionService() {
        if (staticRolePermissionService == null) {
            staticRolePermissionService = SpringBeanUtil.getBean(RolePermissionService.class);
        }
        return staticRolePermissionService;
    }

    /**
     * 私有构造方法
     */
    private AuthUtil() {

    }

    /**
     * 由security上下文环境中获取用户信息
     *
     * @param principal principal
     * @return 当前用户
     */
    public static R<UserDTO> getUserFromPrincipal(Principal principal) {
        if (principal == null) {
            return new R<>(RCode.UNAUTHORIZED);
        }
        UserDTO user = ((SecurityUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        return new R<>(user);
    }

    /**
     * 由security上下文环境中获取角色列表
     *
     * @param principal principal
     * @return 当前用户角色列表
     */
    public static R<List<String>> getRoleListFromPrincipal(Principal principal) {
        List<String> roleList;
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            roleList = ((UsernamePasswordAuthenticationToken) principal).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } else if (principal instanceof OAuth2Authentication) {
            roleList = ((OAuth2Authentication) principal).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } else {
            return new R<>(RCode.AUTH_TYPE_NOT_SUPPORT);
        }
        if (CollectionUtils.isEmpty(roleList)) {
            return new R<>(RCode.ROLE_EMPTY);
        }
        return new R<>(roleList);
    }

    /**
     * 获取路径权限
     *
     * @param url url
     * @return 路径权限
     */
    public static Collection<ConfigAttribute> getResourceDefineValue(String url) {
        return getResourceDefineMap().get(url);
    }

    /**
     * 拉取权限信息
     */
    protected static void loadResourceDefineMap() {
        // todo 多系统权限控制
        List<RolePermissionEntity> permissions = getRolePermissionService().listByOsCode("portal");
        if (!CollectionUtils.isEmpty(permissions)) {
            ConcurrentHashMap<String, Collection<ConfigAttribute>> resultMap = new ConcurrentHashMap<>(permissions.size());
            Map<String, List<RolePermissionEntity>> permissionMap = permissions.stream().collect(Collectors.groupingBy(RolePermissionEntity::getPermissionCode));
            permissionMap.forEach((key, value) -> {
                Collection<ConfigAttribute> configAttributes = value.stream().map(permission -> new SecurityConfig(permission.getRoleCode()))
                        .collect(Collectors.toList());
                resultMap.put("/" + key, configAttributes);
            });
            map = resultMap;
        }
    }

    /**
     * 查询所有权限信息
     */
    private static ConcurrentMap<String, Collection<ConfigAttribute>> getResourceDefineMap() {
        if (CollectionUtils.isEmpty(map)) {
            loadResourceDefineMap();
        }
        return map;
    }
}
