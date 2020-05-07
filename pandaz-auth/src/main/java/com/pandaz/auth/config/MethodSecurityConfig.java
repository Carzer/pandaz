package com.pandaz.auth.config;

import com.pandaz.auth.custom.CustomPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
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
     * 设置自定义权限认证翻译器
     *
     * @param customPermissionEvaluator customPermissionEvaluator
     */
    @Autowired
    public void setCustomPermissionEvaluator(CustomPermissionEvaluator customPermissionEvaluator) {
        this.customPermissionEvaluator = customPermissionEvaluator;
    }

    /**
     * 创建默认表达式
     *
     * @return org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
    }
}
