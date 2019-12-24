package com.pandaz.usercenter.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 授权相关配置
 *
 * @author Carzer
 * @since 2019-12-23
 */
@Configuration
@EnableAuthorizationServer
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 加密私钥
     */
    @Value("${custom.oauth2.privateKey}")
    private String privateKey;

    /**
     * 加密公钥
     */
    @Value("${custom.oauth2.publicKey}")
    private String publicKey;

    /**
     * 客户端数据源
     */
    private final DataSource dataSource;

    /**
     * 授权管理器
     */
    private final AuthenticationManager authenticationManager;

    /**
     * jwt token管理方式  资源服务器需要和授权服务器一致
     *
     * @return jwt管理器
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(privateKey);
        accessTokenConverter.setVerifierKey(publicKey);
        return accessTokenConverter;
    }

    /**
     * jwt token 存储方式
     *
     * @return jwt存储器
     */
    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * 定义令牌端点上的安全约束 /oauth/token_key and /oauth/check_token
     *
     * @param securityConfigurer 授权服务器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer securityConfigurer) {
        securityConfigurer
                .allowFormAuthenticationForClients()
                // permitAll()
//                .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')")
                .tokenKeyAccess("permitAll()")
                // isAuthenticated()
//                .checkTokenAccess("hasRole('TRUSTED_CLIENT')");
                .checkTokenAccess("isAuthenticated()")
        ;
    }

    /**
     * 定义令牌服务
     *
     * @param endpoints 端点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                //jwt存储方式
                .tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtTokenEnhancer())
        ;
    }

    /**
     * 相关的客户端配置包括授权范围 申请方式等
     *
     * @param clients 客户端配置
     * @throws Exception 异常
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 返回一个实现了ClientDetailsService接口的类
     *
     * @return 用户服务
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
}