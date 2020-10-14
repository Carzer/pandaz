package com.github.pandaz.gateway.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pandaz.gateway.entity.GatewayRouteEntity;
import com.github.pandaz.gateway.service.GatewayRouteService;
import com.github.pandaz.gateway.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 路由服务
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RouteServiceImpl implements RouteService {

    /**
     * 路由服务
     */
    private final GatewayRouteService gatewayRouteService;

    /**
     * 获取所有的路由信息
     *
     * @return 路由信息
     */
    @Override
    public List<RouteDefinition> getAll() {
        List<GatewayRouteEntity> list = gatewayRouteService.listAll();
        if (!CollectionUtils.isEmpty(list)) {
            return list.parallelStream().map(this::transferToRoute).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 保存路由信息
     *
     * @param routeDefinition 路由信息
     * @return 执行结果
     */
    @Override
    public int save(RouteDefinition routeDefinition) {
        return 0;
    }

    /**
     * 删除路由信息
     *
     * @param routeId 路由ID
     * @return 执行结果
     */
    @Override
    public int delete(String routeId) {
        return 0;
    }

    /**
     * 转化路由信息
     *
     * @param gatewayRouteEntity entity
     * @return 路由信息
     */
    private RouteDefinition transferToRoute(GatewayRouteEntity gatewayRouteEntity) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteEntity.getRouteId());
        routeDefinition.setUri(URI.create(gatewayRouteEntity.getUri()));
        routeDefinition.setOrder(gatewayRouteEntity.getRouteOrder());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routeDefinition.setFilters(objectMapper.readValue(gatewayRouteEntity.getFilters(), new TypeReference<>() {
            }));
            routeDefinition.setPredicates(objectMapper.readValue(gatewayRouteEntity.getPredicates(), new TypeReference<>() {
            }));
        } catch (IOException e) {
            log.error("网关路由对象转换失败", e);
        }
        return routeDefinition;
    }
}
