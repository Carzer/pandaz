package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.mapper.MenuMapper;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.service.PermissionService;
import com.pandaz.usercenter.task.SimpleTask;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        checkUtil.checkOrSetCode(menuEntity, menuMapper, "菜单编码已存在");
        if (!StringUtils.hasText(menuEntity.getId())) {
            menuEntity.setId(UuidUtil.getId());
        }
        if (!StringUtils.hasText(menuEntity.getParentCode())) {
            menuEntity.setParentCode(CommonConstants.ROOT_MENU_CODE);
        }
        QueryWrapper<MenuEntity> parentWrapper = new QueryWrapper<>();
        parentWrapper.eq("code", menuEntity.getParentCode());
        parentWrapper.eq("is_leaf_node", Byte.valueOf("1"));
        MenuEntity parent = new MenuEntity();
        parent.setIsLeafNode(Byte.valueOf("0"));
        menuMapper.update(parent, parentWrapper);
        return menuMapper.insertSelective(menuEntity);
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
     * 删除的同时，不会直接及联删除子菜单，而是由定时任务进行脏数据的清理
     * {@link SimpleTask#clear()}
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return menuMapper.batchLogicDelete(map);
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
}
