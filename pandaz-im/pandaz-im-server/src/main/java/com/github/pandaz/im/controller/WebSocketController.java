package com.github.pandaz.im.controller;

import com.github.pandaz.commons.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * web socket
 *
 * @author Carzer
 * @since 2020-04-26
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketController {

    /**
     * webSocket
     */
    private final WebSocket webSocket;

    /**
     * 群体发送
     *
     * @return 内容
     */
    @GetMapping("/sendAllWebSocket")
    public R<String> sendAllWebSocket() {
        String text = "这是websocket群体发送！";
        webSocket.sendAllMessage(text);
        return new R<>(text);
    }

    /**
     * 单人发送
     *
     * @param userName userName
     * @return 内容
     */
    @GetMapping("/sendOneWebSocket/{userName}")
    public R<String> sendOneWebSocket(@PathVariable("userName") String userName) {
        String text = userName + " 这是websocket单人发送！";
        webSocket.sendOneMessage(userName, text);
        return new R<>(text);
    }
}
