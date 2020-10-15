package com.github.pandaz.auth.custom;

import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * 表达式处理Handler
 *
 * @author Carzer
 * @since 2020-10-15
 */
public class CustomMethodSecurityExpressionHandler
        extends DefaultMethodSecurityExpressionHandler {

    /**
     * trustResolver
     */
    private final AuthenticationTrustResolver trustResolver =
            new AuthenticationTrustResolverImpl();

    /**
     * 权限信息Provider
     */
    @Setter
    private PermissionProvider permissionProvider;

    /**
     * 创建表达式操作
     *
     * @param authentication 权限
     * @param invocation     反射对象
     * @return 表达式操作
     */
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        CustomMethodSecurityExpressionRoot root =
                new CustomMethodSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());
        root.setPermissionProvider(this.permissionProvider);
        return root;
    }
}