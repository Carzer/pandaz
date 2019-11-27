package com.pandaz.usercenter.client;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.fallback.RedisClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * pandaz:com.pandaz.usercenter.client
 * <p>
 * Redis调用服务
 *
 * @author Carzer
 * Date: 2019-10-28 10:22
 */
@FeignClient(name = "${custom.client.redis-server}", fallbackFactory = RedisClientFallBackFactory.class)
@RequestMapping("/redis")
public interface RedisClient {

    /**
     * 获取Redis value
     *
     * @param key key
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.Object>
     * @author Carzer
     * Date: 2019/10/28 10:46
     */
    @GetMapping("/getValue")
    ExecuteResult<String> getRedisValue(@RequestParam String key);

    /**
     * 设置Redis value
     *
     * @param value value
     * @return com.pandaz.commons.util.ExecuteResult<java.lang.String>
     * @author Carzer
     * Date: 2019/10/28 10:46
     */
    @PostMapping("/setValue")
    ExecuteResult<String> setRedisValue(@RequestParam String value);
}
