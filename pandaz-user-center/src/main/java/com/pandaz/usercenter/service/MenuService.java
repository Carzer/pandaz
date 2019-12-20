package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.MenuEntity;

/**
 * 菜单服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 插入方法
     *
     * @param menu menu
     * @return com.pandaz.usercenter.entity.MenuEntity
     */
    MenuEntity insert(MenuEntity menu);
}
