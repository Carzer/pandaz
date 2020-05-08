package com.github.pandaz.auth.util;

import com.github.pandaz.auth.custom.CustomTokenEnhancer;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具
 *
 * @author Carzer
 * @since 2020-03-05
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenUtil {

    /**
     * 转换token为JWT token
     */
    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 自定义属性
     */
    private final CustomTokenEnhancer customTokenEnhancer;

    /**
     * 生成token
     * {@link DefaultTokenServices#createAccessToken(OAuth2Authentication) createRefreshToken}
     *
     * @param authentication 授权信息
     * @return token
     */
    public Map<String, Object> generateToken(Authentication authentication) {
        Map<String, Object> resultMap = new HashMap<>(2);
        try {
            // 设置默认client
            BaseClientDetails baseClientDetails = new BaseClientDetails();
            baseClientDetails.setClientId("DEFAULT_CLIENT");
            baseClientDetails.setScope(StringUtils.commaDelimitedListToSet("read,write"));
            // 创建token请求
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(0), baseClientDetails.getClientId(),
                    baseClientDetails.getScope(), "custom");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(baseClientDetails);
            // 创建Authentication
            OAuth2Authentication auth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            // 创建token
            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UuidUtil.getUuid());
            // 设置过期时间
            Date expireAt = new Date(System.currentTimeMillis() + SysConstants.DEFAULT_EXPIRE_SECONDS * 1000L);
            token.setExpiration(expireAt);
            token.setScope(baseClientDetails.getScope());
            // 创建refreshToken
            OAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(UuidUtil.getUuid(), expireAt);
            token.setRefreshToken(refreshToken);
            // 添加自定义信息
            OAuth2AccessToken customToken = customTokenEnhancer.enhance(token, auth2Authentication);
            // 返回jwt token
            OAuth2AccessToken jwtToken = jwtAccessTokenConverter.enhance(customToken, auth2Authentication);
            resultMap.put("token", jwtToken);
            resultMap.put("accessTokenExpireAt", expireAt.getTime());
        } catch (Exception e) {
            log.error("获取token异常：", e);
        }
        return resultMap;
    }
}
