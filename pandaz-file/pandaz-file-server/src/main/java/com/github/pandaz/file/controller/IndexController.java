package com.github.pandaz.file.controller;

import com.github.pandaz.commons.util.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    /**
     * 配置
     */
    private final ServerConfig serverConfig;

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
