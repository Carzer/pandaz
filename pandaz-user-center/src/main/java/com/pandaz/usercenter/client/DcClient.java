package com.pandaz.usercenter.client;

import com.pandaz.usercenter.fallback.DcClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 测试类
 *
 * @author Carzer
 * @since 2019-07-16
 */
@FeignClient(value = "pandaz-redis", fallback = DcClientFallBack.class)
public interface DcClient {

    /**
     * 测试方法
     *
     * @return java.lang.String
     */
    @GetMapping("/dc")
    String consumer();

}