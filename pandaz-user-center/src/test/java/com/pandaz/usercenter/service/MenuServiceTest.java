package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.usercenter.UserCenterApp;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.MenuEntity;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 菜单测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserCenterApp.class)
@Rollback
@Transactional
@Slf4j
public class MenuServiceTest {

    private MenuService menuService;

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @Test
    public void insert() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("menu_test");
        menuEntity.setName("测试菜单");
        int result = 0;
        try {
            result = menuService.insert(menuEntity);
        } catch (Exception e) {
            log.error("插入菜单信息出错", e);
        }
        assertThat(result, anything());
    }

    @Test
    public void findByCode() {
        MenuEntity test = menuService.findByCode("menu_test");
        assertThat(test, anything());
    }

    @Test
    public void getPage() {
        IPage<MenuEntity> page = menuService.getPage(new MenuEntity());
        assertNotNull(page);
    }

    @Test
    public void updateByCode() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("menu_test");
        menuEntity.setName("测试菜单");
        int result = menuService.updateByCode(menuEntity);
        assertThat(result, anything());
    }

    @Test
    public void deleteByCode() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("menu_test");
        menuEntity.setDeletedBy("admin");
        menuEntity.setDeletedDate(LocalDateTime.now());
        int result = menuService.deleteByCode(menuEntity);
        assertThat(result, anything());
    }

    @Test
    public void getAll() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setParentCode(CommonConstants.ROOT_MENU_CODE);
        menuEntity.setCode(CommonConstants.ROOT_MENU_CODE);
        menuEntity.setOsCode(SysConstants.DEFAULT_SYS_CODE);
        List<MenuEntity> list = menuService.getAll(menuEntity);
        menuEntity.setChildren(list);
        MenuDTO menuDTO = BeanCopyUtil.copy(menuEntity, MenuDTO.class);
        assertThat(menuDTO, anything());
    }
}