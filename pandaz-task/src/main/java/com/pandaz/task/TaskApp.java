package com.pandaz.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * pandaz:com.pandaz.task
 * <p>
 * 定时任务相关服务
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TaskApp {

    public static void main(String[] args) {
        SpringApplication.run(TaskApp.class, args);
    }

}
