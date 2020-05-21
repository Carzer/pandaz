package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.util.TokenUtil;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import springfox.documentation.annotations.ApiIgnore;

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
@Api(value = "Token", tags = "自定义token")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refresh_token", value = "refresh_token", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "token刷新方法", notes = "只为已登陆用户提供服务")
    @GetMapping("/refresh")
    public R<HashMap<String, Object>> refreshToken(@RequestParam("refresh_token") String refreshTokenStr, @ApiIgnore Principal principal) {
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
