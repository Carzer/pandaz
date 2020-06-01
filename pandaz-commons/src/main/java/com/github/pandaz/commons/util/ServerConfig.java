package com.github.pandaz.commons.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
     * 获取URL
     *
     * @return URL
     */
    public String getWakeUrl() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return "http://" + address.getHostAddress() + ":" + this.serverPort;
        } catch (UnknownHostException | NullPointerException e) {
            log.error("获取应用地址出错了", e);
        }
        return "http://localhost:" + this.serverPort;
    }

    /**
     * web服务初始化完成
     *
     * @param event event
     */
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        log.debug("Get WebServer port {}", serverPort);
    }
}