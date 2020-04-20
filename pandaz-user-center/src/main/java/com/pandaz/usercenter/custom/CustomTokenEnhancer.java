package com.pandaz.usercenter.custom;

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
        additionalInfo.put("customInfo", "这里留给自定义信息");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}