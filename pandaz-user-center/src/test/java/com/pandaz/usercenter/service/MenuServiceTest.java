package com.pandaz.usercenter.service;

import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.usercenter.BasisUnitTest;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.MenuEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        menuEntity.setDeletedBy("admin");
        menuEntity.setDeletedDate(LocalDateTime.now());
        menuService.deleteByCode(menuEntity);
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
        System.out.println(menuDTO);
    }

    @Test
    public void deleteByCodes() {
        int size = menuService.deleteByCodes("test", LocalDateTime.now(), List.of("test"));
        System.out.println("删除结束:" + size);
    }
}