package com.pandaz.auth.client.fallback;

import com.pandaz.commons.code.RCode;
import com.pandaz.commons.util.R;
import com.pandaz.auth.client.RedisClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Redis fallback
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
            public R<Object> getObject(String key) {
                return new R<>(RCode.FAILED);
            }

            @Override
            public R<String> setObject(String key, String value, long ttl) {
                return R.fail();
            }
        };
    }
}
