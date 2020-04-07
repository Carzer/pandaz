package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.MenuEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 菜单mapper
 *
 * @author Carzer
 * @since 2019-11-01
 */
public interface MenuMapper extends UcBaseMapper<MenuEntity> {

    /**
     * 获取菜单树
     *
     * @param menuEntity menuEntity
     * @return 菜单树
     */
    List<MenuEntity> getAllAsTree(MenuEntity menuEntity);

    /**
     * 根据父级编码查询
     *
     * @param parentCode 父级编码
     * @return 菜单列表
     */
    List<MenuEntity> selectByParentCode(@Value("parentCode") String parentCode);

    /**
     * 获取没有父级的菜单编码
     *
     * @return 菜单编码
     */
    List<String> listMenusWithoutParent();
}