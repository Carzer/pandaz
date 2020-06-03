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
     * Swagger相关配置
     */
    private Swagger swagger;

    /**
     * 随机数
     */
    private Integer random;

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

    /**
     * Swagger相关配置
     */
    @Getter
    @Setter
    public static class Swagger {

        /**
         * oauth2授权服务器地址
         */
        private String authServer = "http://localhost:9007";

        /**
         * api基础包
         */
        private String basePackage;

        /**
         * 标题
         */
        private String title;

        /**
         * 简介
         */
        private String description;

        /**
         * 作者
         */
        private String name;

        /**
         * url
         */
        private String url;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 版本
         */
        private String version;
    }
}
