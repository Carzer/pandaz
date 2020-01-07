package com.pandaz.redis;

import com.pandaz.redis.service.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Redis 测试类
 *
 * @author Carzer
 * @since 2019-10-23
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisTest extends BasisUnitTest {

    private final RedisHelper redisHelper;

    @Test
    public void test() {
//        redisHelper.setObject("test", "test_pandaz");

        System.out.println(redisHelper.getObject("test"));
    }
}
