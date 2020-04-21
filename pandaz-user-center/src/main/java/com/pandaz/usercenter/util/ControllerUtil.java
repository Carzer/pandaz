package com.pandaz.usercenter.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.constants.CommonConstants;
import com.pandaz.commons.dto.usercenter.*;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.*;
import com.pandaz.usercenter.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ControllerUtil
 *
 * @author Carzer
 * @since 2020-03-26
 */
@SuppressWarnings("rawtypes")
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ControllerUtil<S extends UcBaseService> {

    /**
     * 是否简化错误的返回信息
     */
    @Value("${custom.simpleErrorMessage}")
    private boolean simpleErrorMessage;

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
     * 执行删除方法并获取结果
     *
     * @param service     调用服务
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       删除编码
     * @return 执行结果
     */
    public ExecuteResult<String> getDeleteResult(
            S service, String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            service.deleteByCodes(deletedBy, deletedDate, codes);
            result.setData("删除成功");
        } catch (Exception e) {
            log.error("删除方法异常：", e);
            result.setError(errorMsg(e, "删除方法异常"));
        }
        return result;
    }

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
    public MenuDTO getAllMenu(MenuDTO menuDTO, Boolean superAdmin) {
        MenuEntity menuEntity = BeanCopyUtil.copy(menuDTO, MenuEntity.class);
        menuEntity.setParentCode(CommonConstants.ROOT_MENU_CODE);
        List<MenuEntity> list = menuService.getAll(menuEntity);
        menuEntity.setChildren(list);
        return transferToDTO(menuEntity, superAdmin);
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
        Map<String, Object> map = new HashMap<>(1);
        map.put("os_code", osCode);
        List<MenuEntity> list = menuService.listByMap(map);
        return BeanCopyUtil.copyList(list, MenuDTO.class);
    }

    /**
     * 将entity及children转换为dto
     *
     * @param menuEntity entity
     * @return dto
     */
    private MenuDTO transferToDTO(MenuEntity menuEntity, Boolean superAdmin) {
        MenuDTO menuDTO = new MenuDTO();
        List<MenuEntity> entityList = menuEntity.getChildren();
        if (!CollectionUtils.isEmpty(entityList)) {
            List<MenuDTO> dtoList = new ArrayList<>();
            entityList.forEach(menu -> dtoList.add(transferToDTO(menu, superAdmin)));
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
        if (superAdmin) {
            menuDTO.setBitResult(SysConstants.TOTAL_DIGIT_RESULT);
        }
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

    /**
     * 根据环境获取错误信息
     *
     * @param e          错误信息
     * @param simpleText 简化的说明信息
     * @return 错误信息
     */
    public String errorMsg(Exception e, String simpleText) {
        if (simpleErrorMessage) {
            return simpleText;
        }
        return e.getMessage();
    }
}
