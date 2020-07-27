package com.github.pandaz.redis.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

/**
 * redis集群配置
 *
 * @author Carzer
 * @since 2020-07-27
 */
@SuppressWarnings("rawtypes")
@Configuration
@ConditionalOnProperty(prefix = "spring.redis-sentinel", name = "enable", havingValue = "true")
public class RedisSentinelConfig {

    /**
     * redis 哨兵配置
     */
    private RedisSentinelProperties redisSentinelProperties;

    /**
     * 注入方法
     *
     * @param redisSentinelProperties redis 哨兵配置
     */
    @Autowired
    public void setRedisSentinelProperties(RedisSentinelProperties redisSentinelProperties) {
        this.redisSentinelProperties = redisSentinelProperties;
    }

    /**
     * 集群连接池配置
     *
     * @return 集群连接池配置
     */
    @Bean("sentinelPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis-sentinel.lettuce.pool")
    public GenericObjectPoolConfig<Object> redisPool() {
        return new GenericObjectPoolConfig<>();
    }

    /**
     * redis集群配置
     *
     * @return redis集群配置
     */
    @Bean("redisSentinelConfiguration")
    public RedisSentinelConfiguration redisConfig() {
        String master = redisSentinelProperties.getSentinel().getMaster();
        Set<String> sentinels = Set.of(redisSentinelProperties.getSentinel().getNodes().split(","));
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(master, sentinels);
        redisSentinelConfiguration.setPassword(redisSentinelProperties.getPassword());
        return redisSentinelConfiguration;
    }

    /**
     * lettuceSentinelConnectionFactory
     *
     * @param config      连接池配置
     * @param redisConfig redis集群配置
     * @return lettuceSentinelConnectionFactory
     */
    @Bean("lettuceSentinelConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("sentinelPoolConfig") GenericObjectPoolConfig<Object> config,
                                                             @Qualifier("redisSentinelConfiguration") RedisSentinelConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }

    /**
     * redisSentinelTemplate
     *
     * @param lettuceConnectionFactory lettuceConnectionFactory
     * @return redisSentinelTemplate
     */
    @Bean("redisSentinelTemplate")
    public RedisTemplate<String, Object> getRedisSentinelTemplate(@Qualifier("lettuceSentinelConnectionFactory") LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
