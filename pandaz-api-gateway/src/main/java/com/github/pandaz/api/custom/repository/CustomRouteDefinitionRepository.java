package com.github.pandaz.api.custom.repository;

import com.github.pandaz.api.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 路由信息
 *
 * @author Carzer
 * @since 2020-06-15
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomRouteDefinitionRepository implements RouteDefinitionRepository {

    /**
     * 路由服务
     */
    private final RouteService routeService;

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routeService.getAll());
    }

    /**
     * 保存路由信息
     *
     * @param route 路由
     * @return 执行结果
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            routeService.save(routeDefinition);
            return Mono.empty();
        });
    }

    /**
     * 删除路由信息
     *
     * @param routeId 路由ID
     * @return 执行结果
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            routeService.delete(id);
            return Mono.empty();
        });
    }
}
