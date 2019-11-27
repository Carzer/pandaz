package com.pandaz.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.mongodb
 * <p>
 * MongoDb相关服务
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class MongoDbApp {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} MongoDbApp 启动成功 {}", repeat, repeat);
    }

}
