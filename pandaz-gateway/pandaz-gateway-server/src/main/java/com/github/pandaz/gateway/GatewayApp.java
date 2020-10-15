package com.github.pandaz.gateway;

import com.github.pandaz.commons.interceptor.FeignOauth2RequestInterceptor;
import com.github.pandaz.commons.util.SpringBeanUtil;
import com.github.pandaz.gateway.controller.IndexController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * api gateway 负责请求转发
 *
 * @author Carzer
 * @since 2019-07-22
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.github.pandaz",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {com.github.pandaz.commons.controller.IndexController.class}
        )
)
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(value = "com.github.pandaz", defaultConfiguration = FeignOauth2RequestInterceptor.class)
public class GatewayApp {

    public static void main(String[] args) {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/gateway/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/gateway/nacos/naming");
        // 启动项目
        SpringApplication.run(GatewayApp.class, args);
        SpringBeanUtil.getBean(IndexController.class).onStartUp();
        String repeat = "=".repeat(20);
        log.warn("{} GatewayApp 启动成功 {}", repeat, repeat);
    }
}
