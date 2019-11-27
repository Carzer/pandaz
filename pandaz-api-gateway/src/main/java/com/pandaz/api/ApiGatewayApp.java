package com.pandaz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * pandaz:com.pandaz.api
 * <p>
 * api gateway 负责请求转发
 *
 * @author Carzer
 * Date: 2019-07-22 13:29
 */
@SpringBootApplication
@EnableZuulProxy
@SuppressWarnings("unchecked")
public class ApiGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }

    /**
     * swagger api相关配置
     */
    @Component
    @Primary
    private static class DocumentationConfig implements SwaggerResourcesProvider {
        @Override
        public List<SwaggerResource> get() {
            List resources = new ArrayList<>();
            resources.add(swaggerResource("pandaz-file-server", "/pandaz-file-server/v2/api-docs", "1.0"));
            resources.add(swaggerResource("pandaz-user-center", "/pandaz-user-center/v2/api-docs", "1.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }
}
