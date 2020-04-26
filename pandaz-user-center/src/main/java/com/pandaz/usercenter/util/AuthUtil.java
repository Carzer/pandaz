package com.pandaz.usercenter.util;

import com.pandaz.commons.SecurityUser;
import com.pandaz.commons.code.RCode;
import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
     * 查询所有权限信息
     */
    private static ConcurrentMap<String, Collection<ConfigAttribute>> getResourceDefineMap() {
        if (CollectionUtils.isEmpty(map)) {
            loadResourceDefineMap();
        }
        return map;
    }
}