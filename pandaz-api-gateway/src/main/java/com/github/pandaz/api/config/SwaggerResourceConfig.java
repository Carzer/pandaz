package com.github.pandaz.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/***
 * Swagger资源配置
 * @author Carzer
 * @since 2020-05-20
 */
@Component
@Primary
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    /**
     * 路由工具
     */
    private final RouteLocator routeLocator;

    /**
     * spring cloud gateway配置
     */
    private final GatewayProperties gatewayProperties;

    /**
     * 获取文档资源
     *
     * @return 文档资源
     */
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(route -> route.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(
                                swaggerResource(route.getId(),
                                        predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("**", "v2/api-docs"))
                        ))
                );
        return resources;
    }

    /**
     * 文档资源
     *
     * @param name     文档名称
     * @param location 文档位置
     * @return 文档资源
     */
    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
