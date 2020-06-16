package com.github.pandaz.api.service;

import com.github.pandaz.api.entity.GatewayRouteEntity;
import com.github.pandaz.commons.service.BaseService;

import java.util.List;

/**
 * 网关路由表 服务类
 *
 * @author Carzer
 * @since 2020-06-15
 */
public interface GatewayRouteService extends BaseService<GatewayRouteEntity> {

    /**
     * 列出所有路由信息
     *
     * @return 路由信息
     */
    List<GatewayRouteEntity> listAll();
}
