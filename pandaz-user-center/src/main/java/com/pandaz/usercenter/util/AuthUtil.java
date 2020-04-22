package com.pandaz.usercenter.util;

import com.pandaz.commons.custom.SecurityUser;
import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.util.Result;
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
     * 由security上下文环境中获取用户信息
     *
     * @param principal principal
     * @return 当前用户
     */
    public static Result<UserDTO> getUserFromPrincipal(Principal principal) {
        Result<UserDTO> result = new Result<>();
        try {
            if (principal == null) {
                result.setError("用户未登陆！");
                return result;
            }
            UserDTO user = ((SecurityUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
            result.setData(user);
        } catch (Exception e) {
            log.error("获取用户失败:", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 查询所有权限信息
     */
    private static ConcurrentMap<String, Collection<ConfigAttribute>> getResourceDefineMap() {
        if (CollectionUtils.isEmpty(map)) {
            List<RolePermissionEntity> permissions = getRolePermissionService().list();
            if (!CollectionUtils.isEmpty(permissions)) {
                map = new ConcurrentHashMap<>(permissions.size());
                Map<String, List<RolePermissionEntity>> permissionMap = permissions.stream().collect(Collectors.groupingBy(RolePermissionEntity::getPermissionCode));
                permissionMap.forEach((key, value) -> {
                    Collection<ConfigAttribute> configAttributes = value.stream().map(permission -> new SecurityConfig(permission.getRoleCode()))
                            .collect(Collectors.toList());
                    map.put("/" + key, configAttributes);
                });
            }
        }
        return map;
    }
}
