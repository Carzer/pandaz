package com.pandaz.auth.client;

import com.pandaz.commons.util.R;
import com.pandaz.auth.client.fallback.RedisClientFallBackFactory;
import com.pandaz.auth.custom.interceptor.FeignOauth2RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Redis调用服务
 *
 * @author Carzer
 * @since 2019-10-28
 */
@FeignClient(name = "${custom.client.redis-server}", fallbackFactory = RedisClientFallBackFactory.class, configuration = FeignOauth2RequestInterceptor.class)
@RequestMapping("/redis")
public interface RedisClient {

    /**
     * 获取Redis value
     *
     * @param key key
     * @return 执行结果
     */
    @GetMapping("/getObject")
    R<Object> getObject(@RequestParam String key);

    /**
     * 设置Redis value
     *
     * @param key   key
     * @param value value
     * @param ttl   存活时间
     * @return 执行结果
     */
    @PostMapping("/setObject")
    R<String> setObject(@RequestParam String key, @RequestParam String value, @RequestParam long ttl);
}
