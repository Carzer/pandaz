package com.pandaz.act;

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
public class ActApp {
    public static void main(String[] args) {
        SpringApplication.run(ActApp.class, args);
    }
}
