package com.pandaz.redis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认controller
 *
 * @author Carzer
 * @since 2019-07-18
 */
@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IndexController {

    private final DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    public String dc() {
        String services = String.format("Services: %s", discoveryClient.getServices());
        log.info(services);
        return services;
    }

}
