package com.pandaz.auth.service;

import com.pandaz.commons.service.BaseService;
import com.pandaz.auth.entity.MenuEntity;

import java.util.List;

/**
 * 菜单服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
public interface MenuService extends BaseService<MenuEntity> {

    /**
     * 获取所有菜单
     *
     * @param menuEntity menuEntity
     * @return 执行结果
     */
    List<MenuEntity> getAll(MenuEntity menuEntity);

    /**
     * 根据系统编码删除
     *
     * @param menuEntity 删除信息
     * @return 执行结果
     */
    int deleteByOsCode(MenuEntity menuEntity);

    /**
     * 获取没有父级的菜单编码
     *
     * @return 菜单编码
     */
    List<String> listMenusWithoutParent();

    /**
     * 获取已授权的菜单
     *
     * @param osCode   系统编码
     * @param roleList 角色列表
     * @return 菜单列表
     */
    List<MenuEntity> getAuthorizedMenu(String osCode, List<String> roleList);

    /**
     * 只列出叶子节点
     *
     * @param osCode 系统编码
     * @return 菜单列表
     */
    List<MenuEntity> listLeafNode(String osCode);
}
