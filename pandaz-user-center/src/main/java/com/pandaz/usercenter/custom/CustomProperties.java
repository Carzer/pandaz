package com.pandaz.usercenter.custom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义属性
 *
 * @author Carzer
 * @since 2019-11-04
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "custom")
@Component
public class CustomProperties {

    /**
     * 获取全部URL mapping时扫描的包
     */
    private String projectPackage;

    /**
     * 调用客户端的信息
     */
    private Client client;

    /**
     * 缓存参数
     */
    private Cache cache;

    /**
     * 鉴权排除的url列表
     */
    @Value("#{'${custom.excludedPaths}'.split(',')}")
    private String[] excludedPaths;

    /**
     * 调用客户端
     */
    @Getter
    @Setter
    public static class Client {
        private String redisServer;
        private String fileServer;
    }

    /**
     * 缓存参数
     */
    @Getter
    @Setter
    public static class Cache {
        /**
         * 主缓存过期时间单位
         */
        private String masterEntryTtlUnit;
        /**
         * 第二缓存过期时间单位
         */
        private String secondaryEntryTtlUnit;
        /**
         * 主缓存过期时间
         */
        private Integer masterEntryTtl;
        /**
         * 第二缓存过期时间
         */
        private Integer secondaryEntryTtl;
    }
}
