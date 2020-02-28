package com.pandaz.usercenter.custom.handler;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.IpUtil;
import com.pandaz.commons.util.PrintWriterUtil;
import com.pandaz.commons.util.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoginSuccessHandler
 *
 * @author Carzer
 * @since 2019-08-22
 */
@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * 转换token为JWT token
     */
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    /**
     * 过期时间
     */
    private long expireAt;

    /**
     * 登录成功后执行
     *
     * @param httpServletRequest  request
     * @param httpServletResponse response
     * @param authentication      authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException {

        // 输出登录提示信息
        log.debug("用户：[{}]登录", authentication.getName());
        log.debug("IP :{}", IpUtil.getIpAddress(httpServletRequest));
        HashMap<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("authorities", authentication.getAuthorities());
        resultMap.put("user", authentication.getPrincipal());
        // 登陆成功后，返回用户信息的同时返回token
        resultMap.put("token", generateToken(authentication));
        resultMap.put("accessTokenExpireAt", expireAt);
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        result.setData(resultMap);
        PrintWriterUtil.write(httpServletResponse, result);
    }

    /**
     * 生成token
     * {@link DefaultTokenServices#createAccessToken(OAuth2Authentication) createRefreshToken}
     *
     * @param authentication 授权信息
     * @return token
     */
    private OAuth2AccessToken generateToken(Authentication authentication) {
        // 设置默认client
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setClientId("DEFAULT_CLIENT");
        baseClientDetails.setScope(StringUtils.commaDelimitedListToSet("read,write"));
        // 创建token请求
        TokenRequest tokenRequest = new TokenRequest(new ConcurrentHashMap<>(0), baseClientDetails.getClientId(),
                baseClientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(baseClientDetails);
        // 创建token权限
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        // 创建标准token
        long expireTime = 60 * 30 * 1000L;
        expireAt = System.currentTimeMillis() + expireTime;
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UuidUtil.getUuid());
        token.setExpiration(new Date(expireAt));
        token.setScope(baseClientDetails.getScope());
        // 创建refreshToken
        // todo 添加refreshToken的支持,机制、验证
//        OAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(UuidUtil.getUuid(), new Date(System.currentTimeMillis() + expireTime * 2));
//        token.setRefreshToken(refreshToken);
        // 返回jwt token
        return jwtAccessTokenConverter.enhance(token, auth2Authentication);
    }
}
