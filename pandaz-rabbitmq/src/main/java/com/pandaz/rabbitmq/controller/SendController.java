package com.pandaz.rabbitmq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送
 *
 * @author Carzer
 * @since 2019-10-09
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SendController {

    /**
     * 管道
     */
    private final Processor pipe;

    /**
     * 发送消息方法
     *
     * @param message message
     */
    @GetMapping("/send")
    public void send(@RequestParam String message) {
        pipe.output().send(MessageBuilder.withPayload(message).build());
    }
}