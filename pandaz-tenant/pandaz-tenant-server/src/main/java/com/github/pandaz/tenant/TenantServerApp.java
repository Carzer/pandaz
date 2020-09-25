package com.github.pandaz.tenant;

import com.github.pandaz.commons.controller.IndexController;
import com.github.pandaz.commons.interceptor.FeignOauth2RequestInterceptor;
import com.github.pandaz.commons.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 授权中心相关服务
 *
 * @author Carzer
 * @since 2019-07-16
 */
@SpringBootApplication(scanBasePackages = "com.github.pandaz")
@EnableDiscoveryClient
@EnableFeignClients(value = "com.github.pandaz", defaultConfiguration = FeignOauth2RequestInterceptor.class)
@Slf4j
@EnableAsync
public class TenantServerApp {

    /**
     * 设置默认时区
     */
    @PostConstruct
    void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/tenant/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/tenant/nacos/naming");
        // 启动项目
        SpringApplication.run(TenantServerApp.class, args);
        SpringBeanUtil.getBean(IndexController.class).onStartUp();
        String repeat = "=".repeat(20);
        log.warn("{} 授权中心启动成功 {}", repeat, repeat);
    }
}
