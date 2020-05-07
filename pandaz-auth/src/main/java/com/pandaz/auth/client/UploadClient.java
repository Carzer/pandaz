package com.pandaz.auth.client;

import com.pandaz.auth.client.fallback.UploadClientFallBackFactory;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试类
 *
 * @author Carzer
 * @since 2019-07-16
 */
@FeignClient(value = "${custom.client.file-server}", fallbackFactory = UploadClientFallBackFactory.class,
        configuration = UploadClient.MultipartSupportConfig.class)
public interface UploadClient {

    /**
     * 测试方法
     *
     * @param file 上传文件
     * @return java.lang.String
     */
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleFileUpload(@RequestPart(value = "file") MultipartFile file);

    /**
     * 编码配置
     *
     * @author Carzer
     * @since 2019/10/29 10:23
     */
    @Configuration
    class MultipartSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
}