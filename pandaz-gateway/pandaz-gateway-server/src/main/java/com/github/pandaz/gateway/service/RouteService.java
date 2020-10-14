package com.github.pandaz.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 路由服务
 *
 * @author Carzer
 * @since 2020-06-15
 */
public interface RouteService {

    /**
     * 获取所有的路由信息
     *
     * @return 路由信息
     */
    List<RouteDefinition> getAll();

    /**
     * 保存路由信息
     *
     * @param routeDefinition 路由信息
     * @return 执行结果
     */
    int save(RouteDefinition routeDefinition);

    /**
     * 删除路由信息
     *
     * @param routeId 路由ID
     * @return 执行结果
     */
    int delete(String routeId);
}
