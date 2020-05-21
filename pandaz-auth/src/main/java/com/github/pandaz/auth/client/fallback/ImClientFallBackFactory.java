package com.github.pandaz.auth.client.fallback;

import com.github.pandaz.auth.client.WsClient;
import com.github.pandaz.commons.util.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Redis熔断
 *
 * @author Carzer
 * @since 2019-10-28 10:23
 */
@Component
@Slf4j
public class ImClientFallBackFactory implements FallbackFactory<WsClient> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public WsClient create(Throwable cause) {
        ImClientFallBackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new WsClient() {
            @Override
            public R<String> sendAllWebSocket() {
                return R.fail("fallback from client");
            }

            @Override
            public R<String> sendOneWebSocket(String userName) {
                return R.fail("fallback from client");
            }
        };
    }
}
