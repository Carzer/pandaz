package com.github.pandaz.auth.util;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.commons.SecurityUser;
import com.github.pandaz.commons.util.SpringBeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 租户工具类
 *
 * @author Carzer
 * @since 2020-09-07
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TenantUtil {

    /**
     * 验证码存储key
     */
    private static final String TENANT_KEY = "pandaz:auth:tenant:";

    @CreateCache(name = TENANT_KEY, cacheType = CacheType.BOTH, expire = 60, localExpire = 30, timeUnit = TimeUnit.MINUTES)
    private Cache<String, Long> tenantCache;


    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    public Long getTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String name = authentication.getName();
            // 首先从缓存中查询
            Long tenantId = tenantCache.get(name);
            if (tenantId != null) {
                return tenantId;
            }
            // 从token中读取
            if (authentication instanceof OAuth2Authentication) {
                String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
                OAuth2AccessToken oAuth2AccessToken = SpringBeanUtil.getBean(JwtTokenStore.class).readAccessToken(token);
                String tenantIdStr = oAuth2AccessToken.getAdditionalInformation().get(SysConstants.TOKEN_TENANT_ID).toString();
                tenantId = Long.parseLong(tenantIdStr);
                // 从用户信息中读取
            } else if (authentication.getPrincipal() instanceof SecurityUser) {
                tenantId = ((SecurityUser) authentication.getPrincipal()).getUser().getTenantId();
            } else {
                tenantId = -1L;
            }
            tenantCache.put(name, tenantId);
            return tenantId;
        }
        return null;
    }
}
