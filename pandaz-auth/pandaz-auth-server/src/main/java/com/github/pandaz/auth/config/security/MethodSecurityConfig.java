package com.github.pandaz.auth.config.security;

import com.github.pandaz.auth.custom.CustomMethodSecurityExpressionHandler;
import com.github.pandaz.auth.custom.CustomPermissionEvaluator;
import com.github.pandaz.auth.custom.CustomPermissionProvider;
import com.github.pandaz.auth.custom.PermissionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * 注解配置权限相关配置
 *
 * @author Carzer
 * @since 2019-11-05
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * 自定义权限认证翻译器
     */
    private CustomPermissionEvaluator customPermissionEvaluator;

    /**
     * 权限信息Provider
     */
    private PermissionProvider permissionProvider;

    /**
     * 设置自定义权限认证翻译器
     *
     * @param customPermissionEvaluator customPermissionEvaluator
     */
    @Autowired
    public void setCustomPermissionEvaluator(CustomPermissionEvaluator customPermissionEvaluator) {
        this.customPermissionEvaluator = customPermissionEvaluator;
    }

    /**
     * 设置权限信息Provider
     *
     * @param permissionProvider 权限信息Provider
     */
    @Autowired
    public void setPermissionProvider(PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

    /**
     * 创建默认表达式
     *
     * @return org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        expressionHandler.setPermissionProvider(permissionProvider);
        return expressionHandler;
    }

    @Bean
    @ConditionalOnMissingBean(name = "permissionProvider")
    public PermissionProvider permissionProvider() {
        return new CustomPermissionProvider();
    }
}
