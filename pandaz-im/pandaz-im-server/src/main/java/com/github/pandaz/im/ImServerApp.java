package com.github.pandaz.im;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息相关服务
 *
 * @author Carzer
 * @since 2019-07-22
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class ImServerApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/im/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/im/nacos/naming");
        // 启动项目
        SpringApplication.run(ImServerApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} 消息服务启动成功 {}", repeat, repeat);
    }
}
