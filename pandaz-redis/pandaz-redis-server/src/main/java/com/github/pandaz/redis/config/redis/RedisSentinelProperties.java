package com.github.pandaz.redis.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis 哨兵集群配置
 *
 * @author Carzer
 * @since 2020-07-27
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.redis-sentinel")
public class RedisSentinelProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 密码
     */
    private String password;

    /**
     * 哨兵
     */
    private Sentinel sentinel;

    /**
     * 哨兵
     */
    @Getter
    @Setter
    public static class Sentinel {
        /**
         * master
         */
        private String master;

        /**
         * node
         */
        private String nodes;
    }
}
