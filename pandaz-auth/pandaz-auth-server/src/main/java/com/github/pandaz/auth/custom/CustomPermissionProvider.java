package com.github.pandaz.auth.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

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
    private CustomProperties.SuperAdmin superAdmin;

    /**
     * 资源获取Provider
     */
    private MetadataResourceProvider metadataResourceProvider;

    /**
     * 设置通用配置
     *
     * @param customProperties 通用配置
     */
    @Autowired
    public void setSuperAdmin(CustomProperties customProperties) {
        this.superAdmin = customProperties.getSuperAdmin();
    }

    /**
     * 设置资源获取Provider
     *
     * @param metadataResourceProvider 资源获取Provider
     */
    @Autowired
    public void setMetadataResourceProvider(MetadataResourceProvider metadataResourceProvider) {
        this.metadataResourceProvider = metadataResourceProvider;
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
        if (superAdmin.isEnable()) {
            return roleSet.contains(superAdmin.getName());
        }
        Set<String> resources = metadataResourceProvider.getResourceDefineValue(roleSet);

        return resources.contains(resource);
    }
}
