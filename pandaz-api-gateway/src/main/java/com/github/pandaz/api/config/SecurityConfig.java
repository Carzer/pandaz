package com.github.pandaz.api.config;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
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

    @Value("${security.oauth2.resource.jwt.key-uri}")
    private String keyUri;

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
                .pathMatchers(excludedAuthPages).permitAll()
                .anyExchange().authenticated()
                // 必须支持跨域
                .and().csrf().disable()
                .logout().disable();
        // 设置jwtDecoder
        http.oauth2ResourceServer().jwt().jwtDecoder(jwtDecoder());
        return http.build();
    }

    /**
     * 配置jwtDecoder
     *
     * @return jwtDecoder
     */
    @Bean
    ReactiveJwtDecoder jwtDecoder() {
        return WebClient.create().get().uri(URI.create(keyUri))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(JwtPublicKey.class))
                .map(jwtPublicKey -> parsePublicKey(jwtPublicKey.getValue()))
                .map(NimbusReactiveJwtDecoder::new).block();
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
    private final String[] excludedAuthPages = {
            "/auth/login",
            "/auth/logout",
            "/auth/common/**",
            "/auth/oauth/**",
            "/auth/token/**",
            "/auth/userInfo",
            "/health",
            "/api/socket/**"
    };
}