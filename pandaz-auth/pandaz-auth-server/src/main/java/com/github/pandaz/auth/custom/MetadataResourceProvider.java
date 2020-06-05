package com.github.pandaz.auth.custom;

import org.springframework.security.access.ConfigAttribute;

import java.util.Set;

/**
 * 权限信息Provider
 *
 * @author Carzer
 * @since 2020-06-05
 */
public interface MetadataResourceProvider {

    /**
     * 根据角色获取相关权限信息
     *
     * @param roleSet 角色集合
     * @return 权限信息
     */
    Set<ConfigAttribute> getResourceDefineValue(Set<String> roleSet);
}
