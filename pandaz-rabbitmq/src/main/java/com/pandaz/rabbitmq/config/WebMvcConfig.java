package com.pandaz.rabbitmq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * pandaz:com.pandaz.rabbitmq.config
 * <p>
 * WebMvc配置类
 *
 * @author Carzer
 * Date: 2019-07-22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态访问资源
     *
     * @param registry ResourceHandlerRegistry
     * @author Carzer
     * Date: 2019-07-22 15:55
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决 swagger-ui.html 404报错
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }
}