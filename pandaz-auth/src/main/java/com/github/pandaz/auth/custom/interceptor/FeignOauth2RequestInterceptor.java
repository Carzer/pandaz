package com.github.pandaz.auth.custom.interceptor;

import com.github.pandaz.commons.constants.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * feign调用时，自动携带token或sessionID
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Slf4j
@Component
public class FeignOauth2RequestInterceptor implements RequestInterceptor {

    /**
     * 执行方法
     *
     * @param requestTemplate 请求方法
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                Object details = authentication.getDetails();
                // oauth2认证
                if (details instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
                    requestTemplate.header(CommonConstants.AUTHORIZATION, String.format("%s %s", CommonConstants.BEARER_TYPE, auth2AuthenticationDetails.getTokenValue()));
                    // form登陆认证
                } else if (details instanceof WebAuthenticationDetails) {
                    // details中的sessionID非Spring security统一管理的，需使用RequestContextHolder
                    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
                    if (StringUtils.hasText(sessionId)) {
                        requestTemplate.header("Cookie", String.format("SESSION=%s", Base64Utils.encodeToString(sessionId.getBytes())));
                    }
                    // 其他的，后续遇到了再补充
                } else {
                    log.warn("我再想想办法");
                }
            } catch (Exception e) {
                log.error("FeignOauth2RequestInterceptor exception:", e);
            }
        }
    }
}