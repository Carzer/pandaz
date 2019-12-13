package com.pandaz.redis;

import com.pandaz.redis.service.RedisHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * pandaz:com.pandaz.redis
 * <p>
 * Redis 测试类
 *
 * @author Carzer
 * @date 2019-10-23 13:03
 */
public class RedisTest extends BasisUnitTest {
    @Autowired
    private RedisHelper<String, String> redisHelper;

    @Test
    public void test() {
//        redisHelper.setObject("test", "test_pandaz");

        System.out.println(redisHelper.getObject("test"));
    }
}
