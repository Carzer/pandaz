package com.pandaz.config;

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
 * Date: 2019-07-02 18:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigServerApp.class)
@Rollback
public class BasisUnitTest {
    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static long startMsec;
    private static long endMsec;

    /**
     * 方法开始之前
     *
     * <p>
     * setUp 方法的注释
     * @author Carzer
     * Date: 2019-07-02 18:34
     */
    @BeforeClass
    public static void setUp() throws Exception {
        System.out.println("\n###########################################");
        startMsec = System.currentTimeMillis();

        String dateStr = dateformat.format(startMsec);

        System.out.println("test start at : " + dateStr);
        System.out.println(startMsec);
        System.out.println("###########################################");
    }

    /**
     * 方法结束之后
     *
     * <p>
     * tearDown 方法的注释
     * @author Carzer
     * Date: 2019-07-02 18:34
     */
    @AfterClass
    public static void tearDown() {
        System.out.println("\n###########################################");
        endMsec = System.currentTimeMillis();

        String dateStr = dateformat.format(endMsec);

        System.out.println("test end at : " + dateStr);
        System.out.println(endMsec);
        System.out.println("###########################################");
        System.out.println("本次测试耗时 ：" + (endMsec - startMsec) + "毫秒");
        System.out.println("###########################################");
    }
}
