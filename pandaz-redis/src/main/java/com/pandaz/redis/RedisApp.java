package com.pandaz.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.redis
 * <p>
 * Redis相关服务
 *
 * @author Carzer
 * Date: 2019-07-19 10:07
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RedisApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }
}
