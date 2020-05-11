package com.github.pandaz.redis;

import com.github.pandaz.redis.controller.IndexController;
import com.github.pandaz.redis.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Redis相关服务
 *
 * @author Carzer
 * @since 2019-07-19
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class RedisApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/redis/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/redis/nacos/naming");
        // 启动项目
        SpringApplication.run(RedisApp.class, args);
        String repeat = "=".repeat(20);
        SpringBeanUtil.getBean(IndexController.class).onStartUp();
        log.warn("{} RedisApp 启动成功 {}", repeat, repeat);
    }
}
