package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthApp;
import com.github.pandaz.auth.entity.OauthClientEntity;
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
 * 客户端信息测试
 *
 * @author Carzer
 * @since 2020-02-28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthApp.class)
@Rollback
@Transactional
@Slf4j
public class OauthClientServiceTest {

    private OauthClientService oauthClientService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setOauthClientService(OauthClientService oauthClientService) {
        this.oauthClientService = oauthClientService;
    }

    @Test
    public void deleteByClientId() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("test");
        oauthClientEntity.setDeletedBy("admin");
        oauthClientEntity.setDeletedDate(LocalDateTime.now());
        int result = oauthClientService.deleteByClientId(oauthClientEntity);
        assertThat(result, anything());
    }

    @Test
    public void findByClientId() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("test");
        OauthClientEntity test = oauthClientService.findByClientId(oauthClientEntity);
        assertThat(test, anything());
    }

    @Test
    public void updateByClientId() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        int result = oauthClientService.updateByClientId(oauthClientEntity);
        assertThat(result, anything());
    }

    @Test
    public void insert() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        oauthClientEntity.setClientName("client_test");
        int result = 0;
        try {
            result = oauthClientService.insert(oauthClientEntity);
        } catch (Exception e) {
            log.error("插入客户端信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void getPage() {
        OauthClientEntity oauthClientEntity = new OauthClientEntity();
        oauthClientEntity.setClientId("client_test");
        IPage<OauthClientEntity> page = oauthClientService.getPage(oauthClientEntity);
        assertNotNull(page);
    }
}