package com.pandaz.api;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;

/**
 * 单元测试基类
 *
 * @author Carzer
 * @since 2019-07-02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiGatewayApp.class)
@Rollback
public class BasisUnitTest {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static long startMills;

    /**
     * 方法开始之前
     *
     * <p>
     * setUp 方法的注释
     */
    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "logs/api-gateway/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "logs/api-gateway/nacos/naming");
        System.out.println("\n###########################################");
        startMills = System.currentTimeMillis();
        String dateStr = DATE_FORMAT.format(startMills);
        System.out.printf("test start at : %s%n", dateStr);
        System.out.println(startMills);
        System.out.println("###########################################");
    }

    /**
     * 方法结束之后
     *
     * <p>
     * tearDown 方法的注释
     */
    @AfterClass
    public static void tearDown() {
        System.out.println("\n###########################################");
        long endMills = System.currentTimeMillis();
        String dateStr = DATE_FORMAT.format(endMills);
        System.out.printf("test end at : %s%n", dateStr);
        System.out.println(endMills);
        System.out.println("###########################################");
        System.out.printf("本次测试耗时 ：%d毫秒%n", endMills - startMills);
        System.out.println("###########################################");
    }
}
