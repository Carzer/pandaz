package com.pandaz.usercenter.client.fallback;

import com.pandaz.commons.util.Result;
import com.pandaz.usercenter.client.RedisClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Redis熔断
 *
 * @author Carzer
 * @since 2019-10-28 10:23
 */
@Component
@Slf4j
public class RedisClientFallBackFactory implements FallbackFactory<RedisClient> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return 执行结果
     */
    @Override
    public RedisClient create(Throwable cause) {
        RedisClientFallBackFactory.log.error("fallback; reason was: ", cause);
        return new RedisClient() {
            @Override
            public Result<String> getRedisValue(String key) {
                Result<String> result = new Result<>();
                result.setData("fallback from client");
                return result;
            }

            @Override
            public Result<String> setRedisValue(String value) {
                Result<String> result = new Result<>();
                result.setData("nothing from client");
                return result;
            }
        };
    }
}
