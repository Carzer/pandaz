package com.pandaz.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * pandaz:com.pandaz.usercenter
 * <p>
 * 用户中心相关服务
 *
 * @author Carzer
 * Date: 2019-07-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class UserCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApp.class, args);
        String repeat = "=".repeat(20);
        log.warn("{} UserCenter 启动成功 {}", repeat, repeat);
    }

}
