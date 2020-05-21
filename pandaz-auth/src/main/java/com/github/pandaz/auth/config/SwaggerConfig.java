package com.github.pandaz.auth.config;

import com.github.pandaz.auth.custom.CustomProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

/**
 * Swagger2 配置类
 *
 * @author Carzer
 * @since 2019-07-22
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * Swagger相关配置
     */
    private final CustomProperties.Swagger config;

    @Autowired
    public SwaggerConfig(CustomProperties customProperties) {
        this.config = customProperties.getSwagger();
    }

    /**
     * 创建一个Docket对象
     * 调用select()方法，
     * 生成ApiSelectorBuilder对象实例，该对象负责定义外漏的API入口
     * 通过使用RequestHandlerSelectors和PathSelectors来提供Predicate，在此我们使用any()方法，将所有API都通过Swagger进行文档管理
     *
     * @return springfox.documentation.spring.web.plugins.Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(config.getBasePackage()))
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(apiKey(), securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()))
                ;
    }

    /**
     * apiInfo
     *
     * @return springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title(config.getTitle())
                // 简介
                .description(config.getDescription())
                // 作者个人信息
                .contact(new Contact(config.getName(), config.getUrl(), config.getEmail()))
                // 版本
                .version(config.getVersion())
                .build();
    }

    /**
     * 设置oauth2认证方式，这里使用密码模式
     *
     * @return 认证方式
     */
    private SecurityScheme securityScheme() {
        // 密码模式
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(config.getAuthServer() + "/oauth/token");

        return new OAuthBuilder()
                .name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    /**
     * 请求头
     *
     * @return apiKey
     */
    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    /**
     * swagger2 认证的安全上下文
     *
     * @return 上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * 这里是写允许认证的scope
     */
    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope is trusted!")
        };
    }
}
