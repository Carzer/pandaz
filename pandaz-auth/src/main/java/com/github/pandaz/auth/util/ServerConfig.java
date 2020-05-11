package com.github.pandaz.auth.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听
 *
 * @author Carzer
 * @since 2020-05-11
 */
@Component
@Slf4j
@Getter
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {

    /**
     * 服务端口号
     */
    private int serverPort;

    /**
     * web服务初始化完成
     *
     * @param event event
     */
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        log.info("Get WebServer port {}", serverPort);
    }
}