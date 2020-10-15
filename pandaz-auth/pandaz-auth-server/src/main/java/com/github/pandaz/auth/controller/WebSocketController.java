package com.github.pandaz.auth.controller;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.im.api.WsApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * web socket
 *
 * @author Carzer
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ApiIgnore
@PreAuthorize("isAuthenticated()")
public class WebSocketController {

    /**
     * 调用web socket
     */
    private final WsApi wsApi;

    /**
     * 群体发送
     *
     * @return 内容
     */
    @GetMapping("/sendAllWebSocket")
    public R<String> sendAllWebSocket() {
        return wsApi.sendAllWebSocket();
    }

    /**
     * 单人发送
     *
     * @param userName userName
     * @return 内容
     */
    @GetMapping("/sendOneWebSocket/{userName}")
    public R<String> sendOneWebSocket(@PathVariable("userName") String userName) {
        return wsApi.sendOneWebSocket(userName);
    }
}
