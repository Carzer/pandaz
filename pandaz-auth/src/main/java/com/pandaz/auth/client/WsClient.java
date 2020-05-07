package com.pandaz.auth.client;

import com.pandaz.commons.util.R;
import com.pandaz.auth.client.fallback.ImClientFallBackFactory;
import com.pandaz.auth.custom.interceptor.FeignOauth2RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Redis调用服务
 *
 * @author Carzer
 * @since 2019-10-28
 */
@FeignClient(name = "${custom.client.im}", fallbackFactory = ImClientFallBackFactory.class, configuration = FeignOauth2RequestInterceptor.class)
public interface WsClient {

    /**
     * 群体发送
     *
     * @return 内容
     */
    @GetMapping("/sendAllWebSocket")
    R<String> sendAllWebSocket();

    /**
     * 单人发送
     *
     * @param userName userName
     * @return 内容
     */
    @GetMapping("/sendOneWebSocket/{userName}")
    R<String> sendOneWebSocket(@PathVariable("userName") String userName);
}
