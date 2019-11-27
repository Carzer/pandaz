package com.pandaz.usercenter.custom.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * pandaz:com.pandaz.usercenter.custom.properties
 * <p>
 * 自定义属性
 *
 * @author Carzer
 * Date: 2019-11-04 16:36
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "custom")
@Component
public class CustomProperties {

    /**
     * 获取全部URL mapping时扫描的包
     */
    private String projectPackage = "com.pandaz";

    /**
     * 调用客户端的信息
     */
    private final Client client = new Client();

    /**
     * 缓存参数
     */
    private final Cache cache = new Cache();

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
    public static class Cache{
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
