package com.pandaz.usercenter.controller;

import com.pandaz.commons.util.ExecuteResult;
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
    public ExecuteResult<HashMap<String, Object>> refreshToken(@RequestParam("refresh_token") String refreshTokenStr, Principal principal) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                OAuth2RefreshToken refreshToken = jwtTokenStore.readRefreshToken(refreshTokenStr);
                if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                    ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
                    if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                        result.setError("token已过期");
                        return result;
                    }
                }
                HashMap<String, Object> resultMap = new HashMap<>(2);
                resultMap.putAll(tokenUtil.generateToken(authentication));
                result.setData(resultMap);
            }
        } catch (Exception e) {
            log.error("刷新token异常：{}", e.getMessage());
            result.setError(e.getMessage());
        }
        return result;
    }
}
