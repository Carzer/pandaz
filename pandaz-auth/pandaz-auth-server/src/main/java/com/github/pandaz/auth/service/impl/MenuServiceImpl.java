package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.auth.mapper.MenuMapper;
import com.github.pandaz.auth.service.MenuService;
import com.github.pandaz.auth.service.PermissionService;
import com.github.pandaz.auth.util.CheckUtil;
import com.github.pandaz.auth.util.SimpleTask;
import com.github.pandaz.commons.constants.CommonConstants;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    /**
     * 菜单mapper
     */
    private final MenuMapper menuMapper;

    /**
     * 权限服务
     */
    private final PermissionService permissionService;

    /**
     * 编码检查工具
     */
    private final CheckUtil<MenuEntity, MenuMapper> checkUtil;

    /**
     * 插入方法
     *
     * @param menuEntity 菜单信息
     * @return 插入结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(MenuEntity menuEntity) {
        checkUtil.checkOrSetCode(menuEntity, menuMapper, "菜单编码重复");
        if (!StringUtils.hasText(menuEntity.getId())) {
            menuEntity.setId(UuidUtil.getId());
        }
        if (!StringUtils.hasText(menuEntity.getParentCode())) {
            menuEntity.setParentCode(CommonConstants.ROOT_CODE);
        }
        return menuMapper.insertSelective(menuEntity);
    }

    /**
     * 根据编码查询
     *
     * @param menuEntity 菜单编码
     * @return 菜单
     */
    @Override
    public MenuEntity findByCode(MenuEntity menuEntity) {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", menuEntity.getCode());
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(MenuEntity menuEntity) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setMenuCode(menuEntity.getCode());
        permissionEntity.setDeletedBy(menuEntity.getDeletedBy());
        permissionEntity.setDeletedDate(menuEntity.getDeletedDate());
        permissionService.deleteByMenuCode(permissionEntity);
        return menuMapper.logicDelete(menuEntity);
    }

    /**
     * 获取所有菜单
     *
     * @param menuEntity menuEntity
     * @return 执行结果
     */
    @Override
    public List<MenuEntity> getAll(MenuEntity menuEntity) {
        return menuMapper.getAllAsTree(menuEntity);
    }

    /**
     * 批量删除
     * <p>
     * 删除的同时，异步删除子菜单
     * 由定时任务进行脏数据的清理
     * {@link SimpleTask}
     * <p>
     * 由于Spring的异步方法，实际上是异步调用实例方法（以类的实例为单位），{@link this#clearMenuChildren(String, LocalDateTime, List)}无法异步进行
     * 所以在使用@Async注解时，应当使用实例进行调用
     * 这里暂时注释掉，直接同步执行,代码留存备用： SpringBeanUtil.getBean(this.getClass()).clearMenuChildren(deletedBy, deletedDate, codes)
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.setCode(code);
            menuEntity.setDeletedBy(deletedBy);
            menuEntity.setDeletedDate(deletedDate);
            deleteByCode(menuEntity);
        });
        clearMenuChildren(deletedBy, deletedDate, codes);
        return codes.size();
    }

    /**
     * 根据系统编码删除
     *
     * @param menuEntity 删除信息
     * @return 执行结果
     */
    @Override
    public int deleteByOsCode(MenuEntity menuEntity) {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("os_code", menuEntity.getOsCode());
        List<MenuEntity> list = menuMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(this::deleteByCode);
            return list.size();
        }
        return 0;
    }

    /**
     * 获取没有父级的菜单编码
     *
     * @return 菜单编码
     */
    @Override
    public List<String> listMenusWithoutParent() {
        return menuMapper.listMenusWithoutParent();
    }

    /**
     * 获取已授权的菜单
     *
     * @param osCode 系统编码
     * @param roles  角色列表
     * @return 菜单列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MenuEntity> getAuthorizedMenu(String osCode, Set<String> roles) {
        Map<String, Object> map = new HashMap<>(2);
        List<MenuEntity> toRootList = new ArrayList<>();
        Set<String> parentCodes = new HashSet<>();
        map.put("osCode", osCode);
        map.put("list", roles);
        List<MenuEntity> menuList = menuMapper.getAuthorizedMenu(map);
        List<String> allCodes = menuList.parallelStream().map(MenuEntity::getCode).collect(Collectors.toList());
        // 计算位运算和值，并查找缺失的父级菜单
        menuList.forEach(menuEntity -> {
            int bitResult = SysConstants.BASIC_DIGIT_RESULT;
            String parentCode = menuEntity.getParentCode();
            List<Integer> bitResults = menuEntity.getBitResults();
            for (Integer br : bitResults) {
                bitResult |= br;
            }
            menuEntity.setBitResult(bitResult);
            if (!CommonConstants.ROOT_CODE.equals(parentCode) && !allCodes.contains(parentCode)) {
                parentCodes.add(parentCode);
            }
        });
        findToRoot(allCodes, parentCodes, toRootList);
        menuList.addAll(toRootList);
        return menuList;
    }

    /**
     * 只列出叶子节点
     *
     * @param osCode 系统编码
     * @return 菜单列表
     */
    @Override
    public List<MenuEntity> listLeafNode(String osCode) {
        return menuMapper.listLeafNode(osCode);
    }

    /**
     * 清理子菜单
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     */
    @Async
    public void clearMenuChildren(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        try {
            log.debug("清理菜单开始");
            codes.forEach(code -> {
                MenuEntity menuEntity = new MenuEntity();
                menuEntity.setDeletedBy(deletedBy);
                menuEntity.setDeletedDate(deletedDate);
                menuEntity.setCode(code);
                clearChildren(menuEntity);
            });
            log.debug("清理菜单结束");
        } catch (Exception e) {
            log.error("清理子菜单异常：", e);
        }
    }

    /**
     * 清理子菜单
     */
    private void clearChildren(MenuEntity menuEntity) {
        List<MenuEntity> list = menuMapper.selectByParentCode(menuEntity.getCode());
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(menu -> {
                menu.setDeletedBy(menuEntity.getDeletedBy());
                menu.setDeletedDate(menuEntity.getDeletedDate());
                clearChildren(menu);
                deleteByCode(menu);
            });
        }
    }

    /**
     * 找到root以下的父级菜单
     *
     * @param allCodes   所有已查询过的菜单
     * @param codes      将要查询的菜单
     * @param toRootList 所有菜单列表
     */
    private void findToRoot(List<String> allCodes, Set<String> codes, List<MenuEntity> toRootList) {
        // 根据缺失的父级菜单编码进行查询
        List<MenuEntity> menuList = menuMapper.selectByCodes(codes);
        Set<String> parentCodes = new HashSet<>();
        // 将结果放入list备用
        toRootList.addAll(menuList);
        // 将所有查询出的菜单编码放入all菜单编码中
        for (MenuEntity menuEntity : menuList) {
            allCodes.add(menuEntity.getCode());
        }
        // 如果当前查出的菜单，父级编码也未在all编码中存在，则继续查询
        for (MenuEntity menuEntity : menuList) {
            String parentCode = menuEntity.getParentCode();
            if (!CommonConstants.ROOT_CODE.equals(parentCode) && !allCodes.contains(parentCode)) {
                parentCodes.add(parentCode);
            }
        }
        if (!CollectionUtils.isEmpty(parentCodes)) {
            findToRoot(allCodes, parentCodes, toRootList);
        }
    }
}
