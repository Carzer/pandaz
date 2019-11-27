package com.pandaz.usercenter.config;

import com.pandaz.usercenter.custom.evaluator.CustomPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * pandaz:com.pandaz.usercenter.config
 * <p>
 * 注解配置权限相关配置
 *
 * @author Carzer
 * Date: 2019-11-05 11:10
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
     * @author Carzer
     * Date: 2019/11/5 15:33
     */
    @Autowired
    public void setCustomPermissionEvaluator(CustomPermissionEvaluator customPermissionEvaluator) {
        this.customPermissionEvaluator = customPermissionEvaluator;
    }

    /**
     * 创建默认表达式
     *
     * @return org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
     * @author Carzer
     * Date: 2019/11/5 15:35
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(customPermissionEvaluator);
        return expressionHandler;
        //return super.createExpressionHandler();
    }
}
