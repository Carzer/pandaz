package com.pandaz.usercenter.config;

import com.pandaz.usercenter.custom.CustomProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 加载redis相关配置
 *
 * @author Carzer
 * @since 2019-07-02
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisConfig {

    private final CustomProperties customProperties;

    /**
     * redis操作
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return org.springframework.data.redis.core.RedisTemplate<?, ?>
     * <p>
     * getRedisTemplate 方法的注释
     */
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * 缓存配置
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return org.springframework.cache.CacheManager
     * <p>
     * cacheManager 方法的注释
     */
    @Bean("masterCacheManager")
    @Primary
    public CacheManager masterCacheManager(RedisConnectionFactory redisConnectionFactory) {
        CustomProperties.Cache cache = customProperties.getCache();
        Duration duration = Duration.of(cache.getMasterEntryTtl(), ChronoUnit.valueOf(cache.getMasterEntryTtlUnit().toUpperCase()));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存有效期
                .entryTtl(duration);
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 缓存配置
     *
     * @param redisConnectionFactory redisConnectionFactory
     * @return org.springframework.cache.CacheManager
     * <p>
     * cacheManager 方法的注释
     */
    @Bean("secondaryCacheManager")
    public CacheManager secondaryCacheManager(RedisConnectionFactory redisConnectionFactory) {
        CustomProperties.Cache cache = customProperties.getCache();
        Duration duration = Duration.of(cache.getSecondaryEntryTtl(), ChronoUnit.valueOf(cache.getSecondaryEntryTtlUnit().toUpperCase()));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存有效期
                .entryTtl(duration);
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }
}
