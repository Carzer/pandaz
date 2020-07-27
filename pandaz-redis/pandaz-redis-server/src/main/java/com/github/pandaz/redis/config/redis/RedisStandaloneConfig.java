package com.github.pandaz.redis.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis单机配置
 *
 * @author Carzer
 * @since 2020-07-27
 */
@Configuration
public class RedisStandaloneConfig {

    /**
     * 连接池配置
     *
     * @return 连接池配置
     */
    @Bean("standalonePoolConfig")
    @ConfigurationProperties(prefix = "spring.redis-standalone.lettuce.pool")
    public GenericObjectPoolConfig<Object> redisPool() {
        return new GenericObjectPoolConfig<>();
    }

    /**
     * redis单机配置
     *
     * @return redis单机配置
     */
    @Bean("redisStandaloneConfiguration")
    @ConfigurationProperties(prefix = "spring.redis-standalone")
    public RedisStandaloneConfiguration redisConfig() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * redisStandaloneFactory
     *
     * @param config      连接池配置
     * @param redisConfig redis配置
     * @return redisStandaloneFactory
     */
    @Bean("lettuceStandaloneConnectionFactory")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("standalonePoolConfig") GenericObjectPoolConfig<Object> config,
                                                             @Qualifier("redisStandaloneConfiguration") RedisStandaloneConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }

    /**
     * redisStandaloneTemplate
     *
     * @param lettuceConnectionFactory lettuceConnectionFactory
     * @return redisStandaloneTemplate
     */
    @Bean("redisStandaloneTemplate")
    @Primary
    public RedisTemplate<String, Object> getRedisStandaloneTemplate(@Qualifier("lettuceStandaloneConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
