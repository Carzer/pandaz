package com.pandaz.usercenter.client.fallback;

import com.pandaz.commons.util.R;
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
            public R<String> getRedisValue(String key) {
                return R.fail("fallback from client");
            }

            @Override
            public R<String> setRedisValue(String value) {
                return R.fail("nothing from client");
            }
        };
    }
}
