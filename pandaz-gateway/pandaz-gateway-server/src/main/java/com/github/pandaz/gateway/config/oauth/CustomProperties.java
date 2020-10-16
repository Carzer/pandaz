package com.github.pandaz.gateway.config.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义属性
 *
 * @author wanghongqun@jbinfo.cn
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
     * 获取全部URL mapping时扫描的包
     */
    private String publicKey;

    /**
     * 鉴权排除的url列表
     */
    private String[] excludedPaths;

}
