package com.github.pandaz.gateway.config.oauth;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * security配置
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Value("${custom.publicKey}")
    private String publicKey;

    /**
     * 配置请求
     *
     * @param http http请求
     */
    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                // 无需进行权限过滤的请求路径
                .pathMatchers(excludedPaths).permitAll()
                .anyExchange().authenticated()
                // 必须支持跨域
                .and().csrf().disable()
                .logout().disable();
        // 设置jwtDecoder
        http.oauth2ResourceServer().jwt().publicKey(parsePublicKey(publicKey));
        return http.build();
    }

    /**
     * 解析获取的JwtPublicKey
     *
     * @param keyValue value
     * @return RSAPublicKey
     */
    private RSAPublicKey parsePublicKey(String keyValue) {
        PemReader pemReader = new PemReader(new StringReader(keyValue));
        try {
            PemObject pem = pemReader.readPemObject();
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pem.getContent()));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Unable to parse public key", e);
        }
        return null;
    }

    /**
     * security的鉴权排除的url列表
     */
    private final String[] excludedPaths = {
            "/auth/login",
            "/auth/logout",
            "/auth/common/**",
            "/auth/oauth/**",
            "/auth/token/**",
            "/ws/**",
            "/health",
            "/wakeUp",
            "/api/socket/**",
            "/doc.html",
            "/swagger-ui.html",
            "/webjars/**",
            "/service-worker.js",
            "/resources/**",
            "/swagger-resources/**",
            "/**/v2/api-docs"
    };
}