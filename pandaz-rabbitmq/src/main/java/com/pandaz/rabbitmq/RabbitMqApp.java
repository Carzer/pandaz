package com.pandaz.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * rabbitmq相关服务
 *
 * @author Carzer
 * @since 2019-07-22
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RabbitMqApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/rabbitmq/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/rabbitmq/nacos/naming");
        // 启动项目
        SpringApplication.run(RabbitMqApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} RabbitMqApp 启动成功 {}", repeat, repeat);
    }

}
