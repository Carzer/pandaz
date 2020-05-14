package com.github.pandaz.file.util;

import com.github.pandaz.commons.code.FileCode;
import com.github.pandaz.file.FileServerApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ftp服务测试
 *
 * @author Carzer
 * @since 2020-05-12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FileServerApp.class)
@Slf4j
public class FtpUtilTest {

    /**
     * ftp服务
     */
    private FtpUtil ftpUtil;

    @Autowired
    public void setFtpUtil(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/file-server-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/file-server-test/nacos/naming");
    }

    /**
     * 上传测试
     */
    @Test
    public void uploadFile() {
        FileCode fileCode = ftpUtil.uploadFile("/file/test",
                "照片审查明细.xlsx",
                "/Users/carzer/Downloads/照片审查明细.xlsx");
        Assert.assertEquals(FileCode.SUCCESS, fileCode);
    }

    /**
     * 下载测试
     */
    @Test
    public void downloadFile() {
        FileCode fileCode = ftpUtil.downloadFile("/file/test", "照片审查明细.xlsx", "/Users/carzer/Downloads/iso");
        Assert.assertEquals(FileCode.SUCCESS, fileCode);
    }

    /**
     * 删除测试
     */
    @Test
    public void deleteFile() {
        FileCode fileCode = ftpUtil.deleteFile("/file/test", "照片审查明细.xlsx");
        Assert.assertEquals(FileCode.SUCCESS, fileCode);
    }

    /**
     * 删除文件夹测试
     */
    @Test
    public void removeDirectory() {
        FileCode fileCode = ftpUtil.removeDirectory("/file", "test");
        Assert.assertEquals(FileCode.SUCCESS, fileCode);
    }
}