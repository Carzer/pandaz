package com.pandaz.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * pandaz:com.pandaz.rabbitmq.controller
 * <p>
 * 消息发送
 *
 * @author Carzer
 * @date 2019-10-09 13:29
 */
@RestController
public class SendController {

    /**
     * 管道
     */
    @Autowired
    private Processor pipe;

    /**
     * 发送消息方法
     *
     * @param message message
     * @author Carzer
     * @date 2019/10/9 16:39
     */
    @GetMapping("/send")
    public void send(@RequestParam String message) {
        pipe.output().send(MessageBuilder.withPayload(message).build());
    }
}