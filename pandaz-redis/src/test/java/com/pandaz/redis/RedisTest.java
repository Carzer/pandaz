package com.pandaz.redis;

import com.pandaz.redis.service.RedisHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertThat;

/**
 * Redis 测试类
 *
 * @author Carzer
 * @since 2019-10-23
 */
public class RedisTest extends BasisUnitTest {

    private RedisHelper redisHelper;

    @Autowired
    public void setRedisHelper(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Test
    public void getObj() {
        Object test = redisHelper.getObject("test");
        assertThat(test, anything());
    }
}
