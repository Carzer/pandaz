package com.pandaz.auth.config;

import com.pandaz.commons.SecurityUser;
import com.pandaz.auth.custom.CustomDaoAuthenticationProvider;
import com.pandaz.auth.custom.CustomTokenEnhancer;
import com.pandaz.auth.service.OauthClientService;
import com.pandaz.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Set;

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
     * 授权管理器
     */
    private final AuthenticationManager authenticationManager;

    /**
     * 客户端服务
     */
    private final OauthClientService oauthClientService;

    /**
     * 密码加密
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 用户详情信息
     */
    private final UserDetailsService userDetailsService;

    /**
     * 自定义token处理
     */
    private final CustomTokenEnhancer customTokenEnhancer;

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
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
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
        //增加转换链路，以增加自定义属性
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtTokenEnhancer()));
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(refreshTokenUserDetailsService())
                //jwt存储方式,其实就是不存储
                .tokenStore(jwtTokenStore())
//                .accessTokenConverter(jwtTokenEnhancer())
                .tokenEnhancer(enhancerChain)
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
        return oauthClientService::loadClientByClientId;
    }

    /**
     * 返回一个实现UserDetailsService接口的类,供refresh token使用
     * <p>
     * form登陆以及token申请都是先验证,后查权限{@link CustomDaoAuthenticationProvider createSuccessAuthentication}
     * <p>
     * 由于refresh_token的特殊性，所以需要在查询用户的同时查询权限
     * {@link AuthorizationServerEndpointsConfigurer addUserDetailsService(DefaultTokenServices, UserDetailsService)}
     * {@link DefaultTokenServices refreshAccessToken}
     *
     * @return refreshTokenUserDetailsService
     */
    @Bean(name = "refreshTokenUserDetailsService")
    public UserDetailsService refreshTokenUserDetailsService() {
        return loginName -> {
            SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(loginName);
            Set<GrantedAuthority> authorities = roleService.findBySecurityUser(securityUser);
            return new SecurityUser(loginName, securityUser.getPassword(), authorities, securityUser.getUser());
        };
    }
}