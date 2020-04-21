package com.pandaz.rabbitmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 消息接收
 *
 * @author Carzer
 * @since 2019-10-09
 */
@Component
@EnableBinding(Processor.class)
public class MqReceiver {

    /**
     * slf4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqReceiver.class);

    /**
     * 接收方法
     * <p>
     * payload为byte[]格式，condition判断时，需要注意格式
     *
     * @param message message
     */
    @StreamListener(value = Sink.INPUT, condition = "'hi'.equals(new String(payload))")
    public void process(String message) {
        LOGGER.info("Received: {}", message);
    }
}