package com.github.pandaz.file.config.ftp;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ftp配置
 *
 * @author Carzer
 * @since 2020-05-12
 */
@ConfigurationProperties(prefix = "ftp")
@Data
@Component
public class FtpProperties {

    /**
     * 是否开启ftp
     */
    private boolean open = false;

    /**
     * host
     */
    private String host = "localhost";

    /**
     * port
     */
    private int port = FTP.DEFAULT_PORT;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * bufferSize
     */
    private int bufferSize = 8096;

    /**
     * 初始化连接数
     */
    private Integer initialSize = 0;

    /**
     * 最大空闲
     */
    private Integer maxIdle = 8;

    /**
     * 从连接池中获取client的重试次数
     */
    private Integer retries = 3;

    /**
     * 编码
     */
    private String encoding;

    /**
     * 获取时检测
     */
    private boolean testOnBorrow = true;

    /**
     * 归还时检测
     */
    private boolean testOnReturn = true;

    /**
     * 发呆时检测
     */
    private boolean testWhileIdle = true;

    /**
     * 最小可回收空闲时间
     */
    private long minEvictableIdleTimeMillis = 60000L;

    /**
     * 1.连接空闲时间大于softMinEvictableIdleTimeMillis并且当前连接池的空闲连接数大于最小空闲连接数minIdle；
     * 2.连接空闲时间大于minEvictableIdleTimeMillis。
     * 1或者2成立即可回收，是或的关系
     */
    private long softMinEvictableIdleTimeMillis = 50000L;

    /**
     * 每30秒运行一次空闲连接回收器
     */
    private long timeBetweenEvictionRunsMillis = 30000L;
}