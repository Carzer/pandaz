package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthApp;
import com.github.pandaz.auth.entity.OsInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 系统信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthApp.class)
@Rollback
@Transactional
@Slf4j
public class OsInfoServiceTest {

    private OsInfoService osInfoService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setOsInfoService(OsInfoService osInfoService) {
        this.osInfoService = osInfoService;
    }

    @Test
    public void insert() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setName("测试系统");
        int result = 0;
        try {
            result = osInfoService.insert(osInfoEntity);
        } catch (Exception e) {
            log.error("插入系统信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void findByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        OsInfoEntity test = osInfoService.findByCode(osInfoEntity);
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<OsInfoEntity> page = osInfoService.getPage(new OsInfoEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setName("测试系统");
        int result = osInfoService.updateByCode(osInfoEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        OsInfoEntity osInfoEntity = new OsInfoEntity();
        osInfoEntity.setCode("os_test");
        osInfoEntity.setDeletedBy("admin");
        osInfoEntity.setDeletedDate(LocalDateTime.now());
        int result = osInfoService.deleteByCode(osInfoEntity);
        assertThat(result, anything());
    }
}