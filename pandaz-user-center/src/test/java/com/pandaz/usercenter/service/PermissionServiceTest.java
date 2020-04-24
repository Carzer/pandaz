package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.UserCenterApp;
import com.pandaz.usercenter.custom.constants.UrlEnum;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.entity.PermissionEntity;
import lombok.extern.slf4j.Slf4j;
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
@SpringBootTest(classes = UserCenterApp.class)
@Rollback
@Transactional
@Slf4j
public class PermissionServiceTest {

    /**
     * 权限服务
     */
    private PermissionService permissionService;

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
        PermissionEntity test = permissionService.findByCode("per_test");
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
        int result = permissionService.deleteByCode(permissionEntity);
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
                    permissionEntity.setName(menuEntity.getName() + ":" + value.getName());
                    permissionEntity.setCode(menuEntity.getCode() + value.getUrl());
                    permissionService.insert(permissionEntity);
                }
            });
        } catch (Exception e) {
            log.error("初始化权限信息出错", e);
        }
        assertThat(result, anything());
    }
}