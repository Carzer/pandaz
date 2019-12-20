package com.pandaz.task;

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
@SpringBootTest(classes = TaskApp.class)
@Rollback
public class BasisUnitTest {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static long startMills;

    /**
     * 方法开始之前
     *
     * <p>
     * setUp 方法的注释
     *
     */
    @BeforeClass
    public static void setUp() {
        System.out.println("\n###########################################");
        startMills = System.currentTimeMillis();
        String dateStr = DATE_FORMAT.format(startMills);
        System.out.println("test start at : " + dateStr);
        System.out.println(startMills);
        System.out.println("###########################################");
    }

    /**
     * 方法结束之后
     *
     * <p>
     * tearDown 方法的注释
     *
     */
    @AfterClass
    public static void tearDown() {
        System.out.println("\n###########################################");
        long endMills = System.currentTimeMillis();
        String dateStr = DATE_FORMAT.format(endMills);
        System.out.println("test end at : " + dateStr);
        System.out.println(endMills);
        System.out.println("###########################################");
        System.out.println("本次测试耗时 ：" + (endMills - startMills) + "毫秒");
        System.out.println("###########################################");
    }
}
