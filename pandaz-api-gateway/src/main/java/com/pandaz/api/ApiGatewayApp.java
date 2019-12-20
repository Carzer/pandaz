package com.pandaz.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * api gateway 负责请求转发
 *
 * @author Carzer
 * @since 2019-07-22
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class ApiGatewayApp {

    public static void main(String[] args) {
        //设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/api-gateway/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/api-gateway/nacos/naming");
        //启动项目
        SpringApplication.run(ApiGatewayApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} ApiGatewayApp 启动成功 {}", repeat, repeat);
    }
}
