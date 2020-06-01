package com.github.pandaz.redis.controller;

import com.github.pandaz.commons.util.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

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
@ApiIgnore
public class IndexController {

    /**
     * 配置
     */
    private final ServerConfig serverConfig;

    /**
     * 发现服务
     */
    private final DiscoveryClient discoveryClient;

    /**
     * 获取服务
     *
     * @return 服务列表
     */
    @GetMapping("/dc")
    public String dc() {
        String services = String.format("Services: %s", discoveryClient.getServices());
        log.info(services);
        return services;
    }

    @GetMapping("wakeUp")
    public void wake() {
        log.debug("wake up a person who pretends to be asleep");
    }

    /**
     * 应用启动后执行
     */
    public void onStartUp() {
        new RestTemplate().getForEntity(String.format("%s/wakeUp", serverConfig.getWakeUrl()), String.class);
    }
}
