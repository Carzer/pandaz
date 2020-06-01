package com.github.pandaz.im.api;

import com.github.pandaz.commons.interceptor.FeignOauth2RequestInterceptor;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.im.api.fallback.WsApiFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Redis调用服务
 *
 * @author Carzer
 * @since 2019-10-28
 */
@FeignClient(name = "pandaz-im-server", fallbackFactory = WsApiFallBackFactory.class, configuration = FeignOauth2RequestInterceptor.class)
public interface WsApi {

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
