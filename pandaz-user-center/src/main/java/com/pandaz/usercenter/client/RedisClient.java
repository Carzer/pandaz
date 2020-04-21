package com.pandaz.usercenter.client;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.client.fallback.RedisClientFallBackFactory;
import com.pandaz.usercenter.custom.interceptor.FeignOauth2RequestInterceptor;
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
     * @return redis值
     */
    @GetMapping("/getValue")
    ExecuteResult<String> getRedisValue(@RequestParam String key);

    /**
     * 设置Redis value
     *
     * @param value value
     * @return 执行结果
     */
    @PostMapping("/setValue")
    ExecuteResult<String> setRedisValue(@RequestParam String value);
}
