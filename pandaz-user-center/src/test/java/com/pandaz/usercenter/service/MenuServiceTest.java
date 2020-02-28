package com.pandaz.usercenter.service;

import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.entity.MenuEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单测试
 *
 * @author Carzer
 * @since 2020-02-27
 */
@Transactional
public class MenuServiceTest extends BasisUnitTest {

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
        menuService.insert(menuEntity);
    }

    @Test
    public void findByCode() {
        menuService.findByCode("menu_test");
    }

    @Test
    public void getPage() {
        menuService.getPage(new MenuEntity());
    }

    @Test
    public void updateByCode() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("menu_test");
        menuEntity.setName("测试菜单");
        menuService.updateByCode(menuEntity);
    }

    @Test
    public void deleteByCode() {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("menu_test");
        menuService.deleteByCode(menuEntity);
    }
}