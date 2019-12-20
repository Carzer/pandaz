package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.mapper.MenuMapper;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    /**
     * 菜单mapper
     */
    private final MenuMapper menuMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtils<MenuEntity, MenuMapper> checkUtils;

    /**
     * 插入方法
     *
     * @param menu menu
     * @return com.pandaz.usercenter.entity.MenuEntity
     */
    @Override
    public MenuEntity insert(MenuEntity menu) {
        checkUtils.checkOrSetCode(menu, menuMapper, "菜单编码已存在", null, null);
        menu.setId(UuidUtil.getUnsignedUuid());
        menuMapper.insertSelective(menu);
        return menu;
    }
}
