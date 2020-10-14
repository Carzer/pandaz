package com.github.pandaz.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.AuthServerApp;
import com.github.pandaz.auth.custom.constants.UrlEnum;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.commons.util.UuidUtil;
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
import java.util.List;

import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;

/**
 * 权限测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthServerApp.class)
@Rollback
@Transactional
@Slf4j
public class PermissionServiceTest {

    /**
     * 权限服务
     */
    private PermissionService permissionService;

    @BeforeClass
    public static void setUp() {
        // 设置nacos日志及缓存路径
        System.setProperty("nacos.logging.path", "../../logs/auth-test/nacos");
        System.setProperty("com.alibaba.nacos.naming.cache.dir", "../../logs/auth-test/nacos/naming");
    }

    /**
     * 菜单服务
     */
    private MenuService menuService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Test
    public void insert() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setName("测试权限");
        permissionEntity.setOsCode("portal");
        permissionEntity.setBitDigit(Byte.valueOf("1"));
        permissionEntity.setBitResult(1 >> permissionEntity.getBitDigit());
        int result = permissionService.insert(permissionEntity);
        assertEquals(1, result);
    }

    @Test
    public void findByCode() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        PermissionEntity test = permissionService.findByCode(permissionEntity);
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<PermissionEntity> page = permissionService.getPage(new PermissionEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setName("测试权限");
        int result = permissionService.updateByCode(permissionEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setCode("per_test");
        permissionEntity.setDeletedBy("admin");
        permissionEntity.setDeletedDate(LocalDateTime.now());
        int result = permissionService.logicDeleteByCode(permissionEntity);
        assertThat(result, anything());
    }

    @Test
    public void initPermissions() {
        int result = 0;
        try {
            List<MenuEntity> list = menuService.listLeafNode("portal");
            list.forEach(menuEntity -> {
                for (UrlEnum value : UrlEnum.values()) {
                    PermissionEntity permissionEntity = new PermissionEntity();
                    permissionEntity.setId(UuidUtil.getId());
                    permissionEntity.setCreatedBy("system");
                    permissionEntity.setCreatedDate(LocalDateTime.now());
                    permissionEntity.setOsCode("portal");
                    permissionEntity.setMenuCode(menuEntity.getCode());
                    permissionEntity.setName(menuEntity.getName() + ":" + value.getDesc());
                    permissionEntity.setCode(menuEntity.getCode() + value.getKey());
                    permissionService.insert(permissionEntity);
                }
            });
        } catch (Exception e) {
            log.error("初始化权限信息出错", e);
        }
        assertThat(result, anything());
    }
}