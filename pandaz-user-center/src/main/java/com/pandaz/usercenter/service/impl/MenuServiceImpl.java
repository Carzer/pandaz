package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.mapper.MenuMapper;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * @param menu 菜单信息
     * @return 插入结果
     */
    @Override
    public MenuEntity insert(MenuEntity menu) {
        checkUtils.checkOrSetCode(menu, menuMapper, "菜单编码已存在");
        if (!StringUtils.hasText(menu.getId())) {
            menu.setId(UuidUtil.getId());
        }
        if (!StringUtils.hasText(menu.getParentCode())) {
            menu.setParentCode(CommonConstants.ROOT_MENU_CODE);
        }
        menuMapper.insertSelective(menu);
        return menu;
    }

    /**
     * 根据编码查询
     *
     * @param code 菜单编码
     * @return 菜单
     */
    @Override
    public MenuEntity findByCode(String code) {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return menuMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param menuEntity 菜单信息
     * @return 分页
     */
    @Override
    public IPage<MenuEntity> getPage(MenuEntity menuEntity) {
        Page<MenuEntity> page = new Page<>(menuEntity.getPageNum(), menuEntity.getPageSize());
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(menuEntity.getCode())) {
            queryWrapper.likeRight("code", menuEntity.getCode());
        }
        if (StringUtils.hasText(menuEntity.getName())) {
            queryWrapper.likeRight("name", menuEntity.getName());
        }
        return page(page, queryWrapper);
    }

    /**
     * 根据编码更新
     *
     * @param menuEntity 菜单信息
     * @return 更新结果
     */
    @Override
    public int updateByCode(MenuEntity menuEntity) {
        UpdateWrapper<MenuEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", menuEntity.getCode());
        return menuMapper.update(menuEntity, updateWrapper);
    }

    /**
     * 根据编码删除
     *
     * @param menuEntity 菜单信息
     * @return 删除结果o
     */
    @Override
    public int deleteByCode(MenuEntity menuEntity) {
        UpdateWrapper<MenuEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", menuEntity.getCode());
        return menuMapper.delete(updateWrapper);
    }
}
