package com.pandaz.usercenter.custom.interceptor;

import com.pandaz.usercenter.custom.CustomAccessDecisionManager;
import com.pandaz.usercenter.custom.CustomInvocationSecurityMetadataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Collection;

/**
 * 自定义拦截器
 *
 * @author Carzer
 * @since 2020-04-22
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    /**
     * SecurityMetadataSource
     */
    private final CustomInvocationSecurityMetadataSourceService securityMetadataSource;

    /**
     * accessDecisionManager
     */
    @Autowired
    public void setAccessDecisionManager(CustomAccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    /**
     * 初始化
     */
    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("init CustomFilterSecurityInterceptor");
    }

    /**
     * 过滤
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(filterInvocation);
    }

    /**
     * 判定权限
     * <p>
     * 调用{@link CustomInvocationSecurityMetadataSourceService#getAttributes(Object)}方法获取filterInvocation对应的所有权限
     * 再调用{@link CustomAccessDecisionManager#decide(Authentication, Object, Collection)}方法来校验用户的权限是否足够
     */
    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    /**
     * destroy
     */
    @Override
    public void destroy() {
        log.debug("destroy CustomFilterSecurityInterceptor");
    }

    /**
     * {@link AbstractSecurityInterceptor#getSecureObjectClass()}
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /**
     * {@link AbstractSecurityInterceptor#obtainSecurityMetadataSource()}
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}
