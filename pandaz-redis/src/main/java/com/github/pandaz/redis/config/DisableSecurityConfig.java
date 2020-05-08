package com.github.pandaz.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * security配置
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(prefix = "custom", name = "enableAuth", havingValue = "false")
public class DisableSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置请求
     *
     * @param http http请求
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        ;
    }
}