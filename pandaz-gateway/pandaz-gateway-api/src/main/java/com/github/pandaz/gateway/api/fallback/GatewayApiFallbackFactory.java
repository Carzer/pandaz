package com.github.pandaz.gateway.api.fallback;

import com.github.pandaz.gateway.api.GatewayApi;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.gateway.dto.GatewayRouteDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return new GatewayApi() {
            @Override
            public R<List<GatewayRouteDTO>> getAll() {
                return null;
            }
        };
    }
}
