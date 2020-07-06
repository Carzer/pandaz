package com.github.pandaz.task;

import com.github.pandaz.commons.controller.IndexController;
import com.github.pandaz.commons.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 定时任务相关服务
 *
 * @author Carzer
 * @since 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class TaskServerApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/task/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/task/nacos/naming");
        // 启动项目
        SpringApplication.run(TaskServerApp.class, args);
        SpringBeanUtil.getBean(IndexController.class).onStartUp();
        String repeat = "=".repeat(20);
        log.warn("{} 定时任务启动成功 {}", repeat, repeat);
    }
}
