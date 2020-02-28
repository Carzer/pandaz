package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * @param menu 菜单信息
     * @return 插入结果
     */
    MenuEntity insert(MenuEntity menu);

    /**
     * 根据编码查询
     *
     * @param code 菜单编码
     * @return 菜单
     */
    MenuEntity findByCode(String code);

    /**
     * 分页方法
     *
     * @param menuEntity 菜单信息
     * @return 分页
     */
    IPage<MenuEntity> getPage(MenuEntity menuEntity);

    /**
     * 根据编码更新
     *
     * @param menuEntity 菜单信息
     * @return 更新结果
     */
    int updateByCode(MenuEntity menuEntity);

    /**
     * 根据编码删除
     *
     * @param menuEntity 菜单信息
     * @return 删除结果o
     */
    int deleteByCode(MenuEntity menuEntity);
}
