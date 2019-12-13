package com.pandaz.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.rabbitmq
 * <p>
 * rabbitmq相关服务
 *
 * @author Carzer
 * @date 2019-07-22 13:29
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RabbitMqApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} RabbitMqApp 启动成功 {}", repeat, repeat);
    }
}
