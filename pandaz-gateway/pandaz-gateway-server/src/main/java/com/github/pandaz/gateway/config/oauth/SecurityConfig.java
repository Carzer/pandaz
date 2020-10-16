package com.github.pandaz.gateway.config.oauth;

import com.github.pandaz.gateway.custom.handler.JsonErrorWebExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.stream.Collectors;

/**
 * security配置
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Configuration
@EnableWebFluxSecurity
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

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
                .pathMatchers(customProperties.getExcludedPaths()).permitAll()
                .anyExchange().authenticated()
                // 必须支持跨域
                .and().csrf().disable()
                .logout().disable();
        // 设置jwtDecoder
        http.oauth2ResourceServer().jwt().publicKey(parsePublicKey(customProperties.getPublicKey()));
        return http.build();
    }

    /**
     * 自定义异常处理
     *
     * @return 自定义异常处理
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                                                             ResourceProperties resourceProperties, ObjectProvider<ViewResolver> viewResolvers,
                                                             ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {

        JsonErrorWebExceptionHandler exceptionHandler = new JsonErrorWebExceptionHandler(errorAttributes,
                resourceProperties, serverProperties.getError(), applicationContext);
        exceptionHandler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        log.debug("Init Json Exception Handler Instead Default ErrorWebExceptionHandler Success");
        return exceptionHandler;
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

}