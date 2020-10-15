package com.github.pandaz.auth.custom;

import java.util.Set;

/**
 * 权限信息Provider
 *
 * @author Carzer
 * @since 2020-06-05
 */
public interface PermissionProvider {

    /**
     * 判断角色是否有当前资源权限
     *
     * @param resource 资源
     * @param roleSet  角色
     * @return 是否有权限
     */
    boolean hasAuth(String resource, Set<String> roleSet);
}
