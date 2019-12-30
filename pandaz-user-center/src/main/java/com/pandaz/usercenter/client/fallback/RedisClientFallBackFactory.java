package com.pandaz.usercenter.client.fallback;

import com.pandaz.commons.util.ExecuteResult;
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
     * @return com.pandaz.usercenter.client.RedisClient
     */
    @Override
    public RedisClient create(Throwable cause) {
        RedisClientFallBackFactory.log.error("fallback; reason was: ", cause);
        return new RedisClient() {
            @Override
            public ExecuteResult<String> getRedisValue(String key) {
                ExecuteResult<String> result = new ExecuteResult<>();
                result.setData("fallback from client");
                return result;
            }

            @Override
            public ExecuteResult<String> setRedisValue(String value) {
                ExecuteResult<String> result = new ExecuteResult<>();
                result.setData("nothing from client");
                return result;
            }
        };
    }
}
