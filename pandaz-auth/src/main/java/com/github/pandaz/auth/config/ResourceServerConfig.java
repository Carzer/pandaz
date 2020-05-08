package com.github.pandaz.auth.config;

import com.github.pandaz.commons.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 * 资源服务器配置
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 资源服务名
     */
    @Value("${spring.application.name}")
    private String resourceId;

    /**
     * 配置资源服务名
     *
     * @param resources 资源服务名
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceId);
    }

    /**
     * 配置请求过滤
     *
     * @param http http请求
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatcher(requestMatcher())
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 请求匹配器
     * 判断是否携带token
     */
    @Bean
    public RequestMatcher requestMatcher() {
        return request -> {
            // bearer_token
            String auth = request.getHeader(CommonConstants.AUTHORIZATION);
            boolean haveOauth2Token = StringUtils.hasText(auth) &&
                    (auth.startsWith(CommonConstants.BEARER_TYPE)
                            || auth.startsWith(CommonConstants.BEARER_TYPE.toLowerCase()));
            // access_token
            String accessToken = request.getParameter(CommonConstants.ACCESS_TOKEN);
            boolean haveAccessToken = StringUtils.hasText(accessToken);
            return haveOauth2Token || haveAccessToken;
        };
    }
}
