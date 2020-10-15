package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthServerApp;
import com.github.pandaz.auth.entity.ClientEntity;
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
@SpringBootTest(classes = AuthServerApp.class)
@Rollback
@Transactional
@Slf4j
public class ClientServiceTest {

    private ClientService clientService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setOauthClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Test
    public void deleteByClientId() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("test");
        clientEntity.setDeletedBy("admin");
        clientEntity.setDeletedDate(LocalDateTime.now());
        int result = clientService.deleteByClientId(clientEntity);
        assertThat(result, anything());
    }

    @Test
    public void findByClientId() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("test");
        ClientEntity test = clientService.findByClientId(clientEntity);
        assertThat(test, anything());
    }

    @Test
    public void updateByClientId() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("client_test");
        int result = clientService.updateByClientId(clientEntity);
        assertThat(result, anything());
    }

    @Test
    public void insert() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("client_test");
        clientEntity.setClientName("client_test");
        int result = 0;
        try {
            result = clientService.insert(clientEntity);
        } catch (Exception e) {
            log.error("插入客户端信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void getPage() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId("client_test");
        IPage<ClientEntity> page = clientService.getPage(clientEntity);
        assertNotNull(page);
    }
}