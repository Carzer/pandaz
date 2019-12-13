package com.pandaz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.task
 * <p>
 * 定时任务相关服务
 *
 * @author Carzer
 * @date 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class TaskApp {

    public static void main(String[] args) {
        SpringApplication.run(TaskApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} TaskApp 启动成功 {}",repeat,repeat);
    }

}
