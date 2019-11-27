package com.pandaz.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * pandaz:com.pandaz.eureka
 * <p>
 * 基于Eureka的注册中心
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class EurekaServerApp {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} EurekaServer 启动成功 {}",repeat,repeat);
    }

}
