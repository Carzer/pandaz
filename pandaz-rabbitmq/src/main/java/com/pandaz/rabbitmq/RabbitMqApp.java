package com.pandaz.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * pandaz:com.pandaz.rabbitmq
 * <p>
 * rabbitmq相关服务
 *
 * @author Carzer
 * Date: 2019-07-22 13:29
 */
@SpringBootApplication
public class RabbitMqApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMqApp.class, args);
    }
}
