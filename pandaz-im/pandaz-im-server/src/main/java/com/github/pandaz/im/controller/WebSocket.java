package com.github.pandaz.im.controller;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket
 *
 * @author Carzer
 * @since 2020-04-26
 */
@Component
@ServerEndpoint("/websocket/{userName}")
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class WebSocket {

    /**
     * session
     */
    private Session session;

    /**
     * socket
     */
    private static final CopyOnWriteArraySet<WebSocket> WEB_SOCKETS = new CopyOnWriteArraySet<>();

    /**
     * pool
     */
    private static final Map<String, Session> SESSION_POOL = new HashMap<>();

    /**
     * 开启连接
     *
     * @param session  session
     * @param userName userName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userName") String userName) {
        this.session = session;
        WEB_SOCKETS.add(this);
        SESSION_POOL.put(userName, session);
        log.info("{}【websocket消息】有新的连接，总数为:{}", userName, WEB_SOCKETS.size());
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        WEB_SOCKETS.remove(this);
        log.info("【websocket消息】有新的连接，总数为:{}", WEB_SOCKETS.size());
    }

    /**
     * 接收消息
     *
     * @param message message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:{}", message);
    }

    /**
     * 广播消息
     *
     * @param message message
     */
    public void sendAllMessage(String message) {
        for (WebSocket webSocket : WEB_SOCKETS) {
            log.info("【websocket消息】广播消息:{}", message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("广播消息异常:", e);
            }
        }
    }

    /**
     * 单点消息
     *
     * @param userName userName
     * @param message  message
     */
    public void sendOneMessage(String userName, String message) {
        log.info("【websocket消息】单点消息:{}", message);
        Session s = SESSION_POOL.get(userName);
        if (s != null) {
            try {
                s.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                log.error("单点消息:", e);
            }
        }
    }
}