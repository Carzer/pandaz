package com.pandaz.usercenter.fallback;

import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.client.RedisClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * pandaz:com.pandaz.usercenter.fallback
 * <p>
 * Redis熔断
 *
 * @author Carzer
 * @date 2019-10-28 10:23
 */
@Component
@Slf4j
public class RedisClientFallBackFactory implements FallbackFactory<RedisClient> {

    /**
     * 默认方法
     *
     * @param cause cause
     * @return com.pandaz.usercenter.client.RedisClient
     * @author Carzer
     * @date 2019/10/28 10:24
     */
    @Override
    public RedisClient create(Throwable cause) {
        RedisClientFallBackFactory.log.error("fallback; reason was: ", cause);
        return new RedisClient() {
            @Override
            public ExecuteResult<String> getRedisValue(String key) {
                ExecuteResult<String> result = new ExecuteResult<>();
                result.setData("fallback");
                return result;
            }

            @Override
            public ExecuteResult<String> setRedisValue(String value) {
                ExecuteResult<String> result = new ExecuteResult<>();
                result.setData("nothing");
                return result;
            }
        };
    }
}
