package com.github.pandaz.auth.custom;

import lombok.Getter;
import lombok.Setter;
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
     * 鉴权排除的url列表
     */
    private String[] excludedPaths;

    /**
     * 调用客户端的信息
     */
    private Client client;

    /**
     * 缓存参数
     */
    private Cache cache;

    /**
     * 证码信息
     */
    private Captcha captcha;

    /**
     * 超级管理员信息
     */
    private SuperAdmin superAdmin;

    /**
     * Oauth2信息
     */
    private Oauth2 oauth2;

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

    /**
     * 验证码
     */
    @Getter
    @Setter
    public static class Captcha {
        /**
         * 是否开启验证码
         */
        private boolean enable = true;
    }

    /**
     * 超级管理员
     */
    @Getter
    @Setter
    public static class SuperAdmin {
        /**
         * 是否开启超级管理员
         */
        private boolean enable = true;
        /**
         * 超级管理员角色
         */
        private String name = "ROLE_SUPER_ADMIN";
    }

    /**
     * oauth2
     */
    @Getter
    @Setter
    public static class Oauth2 {
        /**
         * oauth2 私钥
         */
        private String privateKey;
        /**
         * oauth2 公钥
         */
        private String publicKey;
    }
}
