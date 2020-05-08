package com.github.pandaz.auth.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pandaz.auth.entity.*;
import com.github.pandaz.auth.service.*;
import com.github.pandaz.commons.constants.CommonConstants;
import com.github.pandaz.commons.dto.auth.*;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.auth.entity.*;
import com.github.pandaz.auth.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ControllerUtil
 *
 * @author Carzer
 * @since 2020-03-26
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControllerUtil {

    /**
     * 系统信息服务
     */
    private final OsInfoService osInfoService;

    /**
     * 字典类型服务
     */
    private final DictTypeService dictTypeService;

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 权限服务
     */
    private final PermissionService permissionService;

    /**
     * 角色-权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 用户服务
     */
    private final UserService userService;

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 获取所有系统信息
     *
     * @return 系统信息
     */
    public List<OsInfoDTO> listAllOs() {
        return BeanCopyUtil.copyList(osInfoService.list(), OsInfoDTO.class);
    }

    /**
     * 获取所有字典类型
     *
     * @return 字典类型
     */
    public List<DictTypeDTO> listAllTypes() {
        return BeanCopyUtil.copyList(dictTypeService.list(), DictTypeDTO.class);
    }

    /**
     * 获取所有菜单
     *
     * @return 菜单信息
     */
    public MenuDTO getAllMenu(MenuDTO menuDTO) {
        MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
        menuEntity.setParentCode(CommonConstants.ROOT_MENU_CODE);
        List<MenuEntity> list = menuService.getAll(menuEntity);
        menuEntity.setChildren(list);
        return transferToDTO(menuEntity);
    }

    /**
     * 权限分页
     *
     * @param permissionDTO 查询条件
     * @return 分页结果
     */
    public Map<String, Object> getPermissionPage(PermissionDTO permissionDTO) {
        IPage<PermissionEntity> page = permissionService.getPage(BeanCopyUtil.copy(permissionDTO, PermissionEntity.class));
        return BeanCopyUtil.convertToMap(page, PermissionDTO.class);
    }

    /**
     * 根据系统编码获取所有菜单信息
     *
     * @param osCode 系统编码
     * @return 菜单信息
     */
    public List<MenuDTO> listMenuByOsCode(String osCode) {
        List<MenuEntity> list = menuService.listLeafNode(osCode);
        return BeanCopyUtil.copyList(list, MenuDTO.class);
    }

    /**
     * 将entity及children转换为dto
     *
     * @param menuEntity entity
     * @return dto
     */
    private MenuDTO transferToDTO(MenuEntity menuEntity) {
        MenuDTO menuDTO = new MenuDTO();
        List<MenuEntity> entityList = menuEntity.getChildren();
        if (!CollectionUtils.isEmpty(entityList)) {
            List<MenuDTO> dtoList = new ArrayList<>();
            entityList.forEach(menu -> dtoList.add(transferToDTO(menu)));
            menuDTO.setChildren(dtoList);
        }
        menuDTO.setId(menuEntity.getId());
        menuDTO.setCode(menuEntity.getCode());
        menuDTO.setOsCode(menuEntity.getOsCode());
        menuDTO.setParentCode(menuEntity.getParentCode());
        menuDTO.setName(menuEntity.getName());
        menuDTO.setUrl(menuEntity.getUrl());
        menuDTO.setRouter(menuEntity.getRouter());
        menuDTO.setRemark(menuEntity.getRemark());
        menuDTO.setLocked(menuEntity.getLocked());
        menuDTO.setSorting(menuEntity.getSorting());
        menuDTO.setIsLeafNode(menuEntity.getIsLeafNode());
        return menuDTO;
    }

    /**
     * 根据系统编码、角色编码、菜单编码获取所有权限
     *
     * @param roleCode 角色编码
     * @param osCode   系统编码
     * @param menuCode 菜单编码
     * @return 权限编码
     */
    public List<String> getPermissionCodes(String roleCode, String osCode, String menuCode) {
        if (StringUtils.hasText(roleCode) && StringUtils.hasText(osCode) && StringUtils.hasText(menuCode)) {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setRoleCode(roleCode);
            rolePermissionEntity.setOsCode(osCode);
            rolePermissionEntity.setMenuCode(menuCode);
            return rolePermissionService.listBindCodes(rolePermissionEntity);
        }
        return new ArrayList<>();
    }

    /**
     * 获取用户分页
     *
     * @param userDTO 分页信息
     * @return 分页结果
     */
    public Map<String, Object> getUserPage(UserDTO userDTO) {
        IPage<UserEntity> page = userService.getPage(BeanCopyUtil.copy(userDTO, UserEntity.class));
        return BeanCopyUtil.convertToMap(page, UserDTO.class);
    }

    /**
     * 获取角色分页
     *
     * @param roleDTO 分页信息
     * @return 分页结果
     */
    public Map<String, Object> getRolePage(RoleDTO roleDTO) {
        IPage<RoleEntity> page = roleService.getPage(BeanCopyUtil.copy(roleDTO, RoleEntity.class));
        return BeanCopyUtil.convertToMap(page, RoleDTO.class);
    }
}
