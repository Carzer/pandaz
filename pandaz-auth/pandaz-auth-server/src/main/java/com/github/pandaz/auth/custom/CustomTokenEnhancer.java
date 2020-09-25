package com.github.pandaz.auth.custom;

import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.commons.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义属性
 *
 * @author Carzer
 * @since 2019-12-30
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    /**
     * 增加自定义属性
     *
     * @param accessToken    accessToken
     * @param authentication authentication
     * @return accessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 测试信息
        final Map<String, Object> additionalInfo = new HashMap<>(1);
        Long tenantId = getTenantId(authentication);
        additionalInfo.put(SysConstants.TOKEN_TENANT_ID, tenantId);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    /**
     * 获取租户ID
     *
     * @param authentication authentication
     * @return 租户ID
     */
    private Long getTenantId(OAuth2Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return ((SecurityUser) (authentication.getUserAuthentication()).getPrincipal()).getUser().getTenantId();
    }
}