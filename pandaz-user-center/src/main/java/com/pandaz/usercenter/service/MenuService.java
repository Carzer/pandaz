package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.MenuEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 菜单服务
 *
 * @author Carzer
 * @date 2019-11-01 15:04
 */
public interface MenuService {

    /**
     * 插入方法
     *
     * @param menu menu
     * @return com.pandaz.usercenter.entity.MenuEntity
     * @author Carzer
     * @date 2019/11/1 15:08
     */
    MenuEntity insert(MenuEntity menu);
}
