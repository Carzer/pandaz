package com.github.pandaz.gateway.api.fallback;

import com.github.pandaz.gateway.api.GatewayApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 权限相关接口 fallback
 *
 * @author Carzer
 * @since 2020-06-03
 */
@Slf4j
@Component
public class GatewayApiFallbackFactory implements FallbackFactory<GatewayApi> {

    /**
     * 默认方法
     *
     * @param cause 异常
     * @return 执行结果
     */
    @Override
    public GatewayApi create(Throwable cause) {
        GatewayApiFallbackFactory.log.error("fallback; reason was: {}", cause.getMessage());
        return () -> null;
    }
}
