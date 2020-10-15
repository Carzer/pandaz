package com.github.pandaz.auth.custom;

import cn.hutool.core.annotation.AnnotationUtil;
import com.github.pandaz.commons.annotations.security.PreAuth;
import com.github.pandaz.commons.constants.CommonConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 表达式处理
 *
 * @author Carzer
 * @since 2020-10-15
 */
@Slf4j
@SuppressWarnings("unused")
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    /**
     * filterObject
     */
    @Getter
    @Setter
    private Object filterObject;

    /**
     * returnObject
     */
    @Getter
    @Setter
    private Object returnObject;

    /**
     * target
     */
    private Object target;

    /**
     * 缓存
     */
    private static final Map<String, String> REPLACE_CACHE = new ConcurrentHashMap<>();

    /**
     * 权限信息Provider
     */
    @Setter
    private PermissionProvider permissionProvider;

    /**
     * 构造方法
     *
     * @param authentication authentication
     */
    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /**
     * 自定义表达式
     *
     * @param resource 资源
     * @return 是否可以访问
     */
    public boolean hasAuth(String resource) {
        try {
            if (resource.contains(CommonConstants.REPLACE)) {
                Class<?> clazz = this.target.getClass();
                String replace = REPLACE_CACHE.get(clazz.getName());
                if (!StringUtils.hasText(replace) && AnnotationUtil.hasAnnotation(clazz, PreAuth.class)) {
                    replace = AnnotationUtil.getAnnotationValue(clazz, PreAuth.class);
                    REPLACE_CACHE.put(clazz.getName(), replace);
                }
                resource = resource.replace(CommonConstants.REPLACE, replace);
            }
            // 获取所有角色
            Set<String> roleSet = this.authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

            return this.permissionProvider.hasAuth(resource, roleSet);
        } catch (Exception e) {
            log.error("权限认证出错:", e);
            return false;
        }
    }

    /**
     * 获取target
     */
    @Override
    public Object getThis() {
        return target;
    }

    /**
     * 设置target
     *
     * @param target target
     */
    void setThis(Object target) {
        this.target = target;
    }
}