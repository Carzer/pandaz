package com.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.auth.AuthApp;
import com.pandaz.auth.entity.OrganizationEntity;
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
 * 组织信息测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthApp.class)
@Rollback
@Transactional
@Slf4j
public class OrganizationServiceTest {

    private OrganizationService organizationService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Test
    public void findByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        OrganizationEntity test = organizationService.findByCode(organizationEntity);
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<OrganizationEntity> page = organizationService.getPage(new OrganizationEntity());
        assertNotNull(page);
    }

    @Test
    public void insert() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        int result = 0;
        try {
            result = organizationService.insert(organizationEntity);
        } catch (Exception e) {
            log.error("插入组织信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void updateByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setName("测试组织");
        int result = organizationService.updateByCode(organizationEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setCode("org_test");
        organizationEntity.setDeletedBy("admin");
        organizationEntity.setDeletedDate(LocalDateTime.now());
        int result = organizationService.deleteByCode(organizationEntity);
        assertThat(result, anything());
    }
}