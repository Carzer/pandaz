package com.github.pandaz.act;

import com.github.pandaz.commons.controller.IndexController;
import com.github.pandaz.commons.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Activiti 相关服务
 *
 * @author Carzer
 * @since 2019-07-19
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.github.pandaz",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {com.github.pandaz.commons.handler.SecurityExceptionHandler.class}
        )
)
@EnableDiscoveryClient
@Slf4j
public class ActServerApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/activiti/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/activiti/nacos/naming");
        // 启动项目
        SpringApplication.run(ActServerApp.class, args);
        SpringBeanUtil.getBean(IndexController.class).onStartUp();
        String repeat = "=".repeat(20);
        log.warn("{} 审批流服务启动成功 {}", repeat, repeat);
    }
}
