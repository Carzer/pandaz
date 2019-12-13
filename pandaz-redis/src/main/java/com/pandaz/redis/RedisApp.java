package com.pandaz.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.redis
 * <p>
 * Redis相关服务
 *
 * @author Carzer
 * @date 2019-07-19 10:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RedisApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} RedisApp 启动成功 {}", repeat, repeat);
    }
}
