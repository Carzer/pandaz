package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.MenuEntity;

/**
 * 菜单mapper
 *
 * @author Carzer
 * @since 2019-11-01
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 插入方法
     *
     * @param menu menu
     * @return 插入结果
     */
    int insertSelective(MenuEntity menu);
}