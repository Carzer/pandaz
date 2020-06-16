package com.github.pandaz.api.config.cache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.autoconfigure.LettuceFactory;
import com.alicp.jetcache.autoconfigure.RedisLettuceAutoConfiguration;
import io.lettuce.core.RedisClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * JetCache配置
 *
 * @author Carzer
 * @since 2020-06-05
 */
@EnableMethodCache(basePackages = "com.github.pandaz")
@EnableCreateCacheAnnotation
@Configuration
public class JetCacheConfig {

    /**
     * lettuce
     *
     * @return lettuce
     */
    @Bean(name = "defaultClient")
    @DependsOn(RedisLettuceAutoConfiguration.AUTO_INIT_BEAN_NAME)
    public LettuceFactory defaultClient() {
        return new LettuceFactory("remote.default", RedisClient.class);
    }
}
