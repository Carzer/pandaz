package com.github.pandaz.file;

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
public class FileServerApp {

    public static void main(String[] args) {
        //设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/file-server/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/file-server/nacos/naming");
        //启动项目
        SpringApplication.run(FileServerApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} FileServerApp 启动成功 {}", repeat, repeat);
    }
}
