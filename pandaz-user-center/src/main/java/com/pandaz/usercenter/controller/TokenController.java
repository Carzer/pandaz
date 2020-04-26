package com.pandaz.usercenter.controller;

import com.pandaz.commons.code.RCode;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

/**
 * 自定义token
 * 只为已登陆用户提供服务
 *
 * @author Carzer
 * @since 2020-03-05
 */
@RestController
@RequestMapping("/token")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenController {

    /**
     * token工具
     */
    private final TokenUtil tokenUtil;

    /**
     * jwtTokenStore
     */
    private final JwtTokenStore jwtTokenStore;

    /**
     * 刷新方法
     *
     * @param refreshTokenStr refreshToken
     * @param principal       当前用户
     * @return token
     */
    @GetMapping("/refresh")
    public R<HashMap<String, Object>> refreshToken(@RequestParam("refresh_token") String refreshTokenStr, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2RefreshToken refreshToken = jwtTokenStore.readRefreshToken(refreshTokenStr);
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                return new R<>(RCode.TOKEN_EXPIRED);
            }
        }
        HashMap<String, Object> resultMap = new HashMap<>(2);
        resultMap.putAll(tokenUtil.generateToken(authentication));
        return new R<>(resultMap);
    }
}
