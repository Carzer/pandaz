package com.github.pandaz.file.config.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mongoDB属性
 *
 * @author Carzer
 * @since 2019-07-16
 */
@Component
@ConfigurationProperties(prefix = "mongodb.secondary")
@Data
public class MongoDbSecondaryProperty {

    /**
     * 主机地址IP+端口
     */
    private String host;

    /**
     * 数据库名称
     */
    private String name;

    /**
     * 数据库用户（非必须）
     */
    private String user;

    /**
     * 数据库密码
     */
    private String pwd;
}
