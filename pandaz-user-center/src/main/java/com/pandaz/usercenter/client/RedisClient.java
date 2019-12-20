package com.pandaz.usercenter.client;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.fallback.RedisClientFallBackFactory;
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
@FeignClient(name = "${custom.client.redis-server}", fallbackFactory = RedisClientFallBackFactory.class)
@RequestMapping("/redis")
public interface RedisClient {

    /**
     * 获取Redis value
     *
     * @param key key
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.Object>
     */
    @GetMapping("/getValue")
    ExecuteResult<String> getRedisValue(@RequestParam String key);

    /**
     * 设置Redis value
     *
     * @param value value
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.String>
     */
    @PostMapping("/setValue")
    ExecuteResult<String> setRedisValue(@RequestParam String value);
}
