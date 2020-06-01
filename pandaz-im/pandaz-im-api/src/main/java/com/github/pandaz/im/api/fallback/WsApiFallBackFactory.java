package com.github.pandaz.im.api.fallback;

import com.github.pandaz.commons.util.R;
import com.github.pandaz.im.api.WsApi;
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
public class WsApiFallBackFactory implements FallbackFactory<WsApi> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public WsApi create(Throwable cause) {
        WsApiFallBackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return new WsApi() {
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
