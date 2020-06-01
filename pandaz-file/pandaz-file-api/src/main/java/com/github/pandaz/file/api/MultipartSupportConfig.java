package com.github.pandaz.file.api;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 编码配置
 *
 * @author Carzer
 * @since 2019/10/29 10:23
 */
@Configuration
public class MultipartSupportConfig {
    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}