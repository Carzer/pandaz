package com.pandaz.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 授权中心相关服务
 *
 * @author Carzer
 * @since 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
@EnableAsync
public class AuthApp {

    /**
     * 设置默认时区
     */
    @PostConstruct
    void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/auth/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/auth/nacos/naming");
        // 启动项目
        SpringApplication.run(AuthApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} 授权中心启动成功 {}", repeat, repeat);
    }
}
