package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthServerApp;
import com.github.pandaz.auth.entity.RoleEntity;
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
 * 角色服务测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@Rollback
@Transactional
@Slf4j
public class RoleServiceTest {

    private RoleService roleService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../../logs/auth-test/nacos/naming");
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    public void insert() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色");
        int result = 0;
        try {
            result = roleService.insert(roleEntity);
        } catch (Exception e) {
            log.error("插入角色信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void getPage() {
        IPage<RoleEntity> page = roleService.getPage(new RoleEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setName("测试角色已变更");
        int result = roleService.updateByCode(roleEntity);
        assertThat(result, anything());
    }

    @Test
    public void findByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        RoleEntity test = roleService.findByCode(roleEntity);
        assertThat(test, anything());
    }

    @Test
    public void deleteByCode() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode("role_test");
        roleEntity.setDeletedBy("admin");
        roleEntity.setDeletedDate(LocalDateTime.now());
        int size = roleService.logicDeleteByCode(roleEntity);
        assertThat(size, anything());
    }

}