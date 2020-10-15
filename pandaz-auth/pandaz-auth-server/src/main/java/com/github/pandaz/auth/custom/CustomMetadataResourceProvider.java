package com.github.pandaz.auth.custom;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.github.pandaz.auth.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 权限信息Provider
 *
 * @author Carzer
 * @since 2020-06-05
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomMetadataResourceProvider implements MetadataResourceProvider {

    /**
     * 角色权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

    /**
     * 根据角色获取相关权限信息
     *
     * @param roleSet 角色集合
     * @return 权限信息
     */
    @Override
    public Set<String> getResourceDefineValue(Set<String> roleSet) {
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
