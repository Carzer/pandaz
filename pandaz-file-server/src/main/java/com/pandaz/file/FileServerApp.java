package com.pandaz.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.file
 * <p>
 * Redis相关服务
 *
 * @author Carzer
 * @date 2019-07-19 10:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class FileServerApp {
    public static void main(String[] args) {
        SpringApplication.run(FileServerApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} FileServerApp 启动成功 {}", repeat, repeat);
    }
}
