package com.pandaz.usercenter.controller;

import com.pandaz.commons.util.R;
import com.pandaz.usercenter.client.WsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web socket
 *
 * @author Carzer
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebSocketController {

    /**
     * 调用web socket
     */
    private final WsClient wsClient;

    /**
     * 群体发送
     *
     * @return 内容
     */
    @GetMapping("/sendAllWebSocket")
    public R<String> sendAllWebSocket() {
        return wsClient.sendAllWebSocket();
    }
}
