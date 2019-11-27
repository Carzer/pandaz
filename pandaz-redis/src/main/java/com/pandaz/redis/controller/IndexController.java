package com.pandaz.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * pandaz:com.pandaz.redis.controller
 * <p>
 * 默认controller
 *
 * @author Carzer
 * Date: 2019-07-18 15:32
 */
@RestController
@RequestMapping("/")
@Slf4j
public class IndexController {
    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        log.info(services);
        return services;
    }
}
