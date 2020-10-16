package com.github.pandaz.auth.custom;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.github.pandaz.auth.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 权限信息Provider
 *
 * @author Carzer
 * @since 2020-10-15
 */
@Component
public class CustomPermissionProvider implements PermissionProvider {

    /**
     * 通用配置
     */
    private CustomProperties customProperties;

    /**
     * 角色权限服务
     */
    private RolePermissionService rolePermissionService;

    /**
     * 设置通用配置
     *
     * @param customProperties 通用配置
     */
    @Autowired
    public void setSuperAdmin(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    /**
     * 设置角色权限服务
     *
     * @param rolePermissionService 角色权限服务
     */
    @Autowired
    public void setRolePermissionService(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    /**
     * 判断角色是否有当前资源权限
     *
     * @param resource 资源
     * @param roleSet  角色
     * @return 是否有权限
     */
    @Override
    public boolean hasAuth(String resource, Set<String> roleSet) {
        // 如果开启超级管理员，并拥有符合的角色，则通过所有请求
        if (customProperties.getSuperAdmin().isEnable()) {
            return roleSet.contains(customProperties.getSuperAdmin().getName());
        }
        Set<String> resources = getResourceDefineValue(roleSet);

        return resources.contains(resource);
    }

    /**
     * 根据角色获取相关权限信息
     *
     * @param roleSet 角色集合
     * @return 权限信息
     */
    private Set<String> getResourceDefineValue(Set<String> roleSet) {
        String osCode = customProperties.getOsCode();
        Set<String> allResources = new HashSet<>();
        roleSet.forEach(roleCode -> allResources.addAll(getConfigAttributes(osCode, roleCode)));
        return allResources;
    }

    /**
     * 获取权限信息
     *
     * @param osCode   系统编码
     * @param roleCode 角色编码
     * @return 权限信息
     */
    @Cached(name = "permissions:", key = "#osCode+':'+#roleCode", cacheType = CacheType.LOCAL, expire = 10, timeUnit = TimeUnit.MINUTES)
    private Set<String> getConfigAttributes(String osCode, String roleCode) {
        return new HashSet<>(rolePermissionService.getByOsCodeAndRoleCode(osCode, roleCode));
    }
}
