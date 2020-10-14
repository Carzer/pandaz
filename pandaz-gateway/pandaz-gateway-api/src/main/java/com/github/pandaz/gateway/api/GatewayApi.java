package com.github.pandaz.gateway.api;

import com.github.pandaz.gateway.api.fallback.GatewayApiFallbackFactory;
import com.github.pandaz.commons.util.R;
import com.github.pandaz.gateway.dto.GatewayRouteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 权限相关接口
 *
 * @author Carzer
 * @since 2020-06-03
 */
@FeignClient(name = "${project.artifactId}", fallbackFactory = GatewayApiFallbackFactory.class)
public interface GatewayApi {

    /**
     * 获取所有路由表
     *
     * @return 路由表
     */
    @GetMapping("/getAll")
    R<List<GatewayRouteDTO>> getAll();
}
