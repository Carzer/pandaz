package com.pandaz.usercenter.custom.interceptor;

import com.pandaz.commons.constants.CommonConstants;
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
 * @since 2019-10-25
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
                if (details instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails auth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
                    requestTemplate.header(CommonConstants.AUTHORIZATION, String.format("%s %s", CommonConstants.BEARER_TYPE, auth2AuthenticationDetails.getTokenValue()));
                } else if (details instanceof WebAuthenticationDetails) {
                    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
                    if (StringUtils.hasText(sessionId)) {
                        requestTemplate.header("Cookie", "SESSION=" + Base64Utils.encodeToString(sessionId.getBytes()));
                    }
                } else {
                    log.warn("我再想想办法");
                }
            } catch (Exception e) {
                log.error("FeignOauth2RequestInterceptor exception:", e);
            }
        }
    }
}