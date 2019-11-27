package com.pandaz.act;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.act
 * <p>
 * Activiti 相关服务
 *
 * @author Carzer
 * Date: 2019-07-19 10:07
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class ActApp {
    public static void main(String[] args) {
        SpringApplication.run(ActApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} ActApp 启动成功 {}", repeat, repeat);
    }
}
