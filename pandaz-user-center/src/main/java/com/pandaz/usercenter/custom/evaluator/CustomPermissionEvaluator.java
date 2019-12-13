package com.pandaz.usercenter.custom.evaluator;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * pandaz:com.pandaz.usercenter.custom.evaluator
 * <p>
 * 自定义权限认证翻译器
 *
 * @author Carzer
 * @date 2019-11-04 13:59
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {


    /**
     * 判断是否有权限
     *
     * @param authentication     authentication
     * @param targetDomainObject targetDomainObject
     * @param permission         permission
     * @return boolean
     * @author Carzer
     * @date 2019/11/4 16:07
     */
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Object targetDomainObject, Object permission) {
        if ("user".equals(targetDomainObject)) {
            return this.hasPermission(authentication, permission);
        }
        return false;
    }


    /**
     * 判断是否有权限
     *
     * @param authentication authentication
     * @param targetId       targetId
     * @param targetType     targetType
     * @param permission     permission
     * @return boolean
     * @author Carzer
     * @date 2019/11/4 16:08
     */
    @Override
    public boolean hasPermission(Authentication authentication,
                                 Serializable targetId, String targetType, Object permission) {
        return true;
    }

    /**
     * 简单的字符串比较，相同则认为有权限
     *
     * @param authentication authentication
     * @param permission     permission
     * @return boolean
     * @author Carzer
     * @date 2019/11/4 16:08
     */
    private boolean hasPermission(Authentication authentication, Object permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(permission)) {
                return true;
            }
        }
        return false;
    }
}