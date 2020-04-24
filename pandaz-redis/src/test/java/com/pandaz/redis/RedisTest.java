package com.pandaz.redis;

import com.pandaz.redis.service.RedisHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertThat;

/**
 * Redis 测试类
 *
 * @author Carzer
 * @since 2019-10-23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApp.class)
@Rollback
public class RedisTest {

    private RedisHelper redisHelper;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/api-gateway/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/api-gateway/nacos/naming");
    }

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
