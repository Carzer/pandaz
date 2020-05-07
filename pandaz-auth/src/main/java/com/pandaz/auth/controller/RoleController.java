package com.pandaz.auth.controller;

import com.pandaz.commons.constants.UrlConstants;
import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.auth.*;
import com.pandaz.commons.service.BaseService;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.auth.entity.RoleEntity;
import com.pandaz.auth.entity.RolePermissionEntity;
import com.pandaz.auth.service.RolePermissionService;
import com.pandaz.auth.service.RoleService;
import com.pandaz.auth.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/role")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController extends BaseController<RoleDTO, RoleEntity> {

    /**
     * 角色服务
     */
    private final RoleService roleService;

    /**
     * 角色-权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 工具类
     */
    private final ControllerUtil controllerUtil;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<RoleEntity> getBaseService() {
        return this.roleService;
    }

    /**
     * 分页方法
     *
     * @param roleDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    @Override
    public R<Map<String, Object>> getPage(RoleDTO roleDTO) {
        return new R<>(controllerUtil.getRolePage(roleDTO));
    }

    /**
     * 绑定权限
     *
     * @param rolePermissionDTO 权限信息
     * @param principal         当前用户
     * @return 执行结果
     */
    @PutMapping("/bindPermissions")
    public R<String> bindPermissions(@RequestBody RolePermissionDTO rolePermissionDTO, Principal principal) {
        if (!CollectionUtils.isEmpty(rolePermissionDTO.getPermissionCodes())) {
            RolePermissionEntity rolePermissionEntity = BeanCopyUtil.copy(rolePermissionDTO, RolePermissionEntity.class);
            rolePermissionService.bindPermissions(principal.getName(), LocalDateTime.now(), rolePermissionEntity);
        }
        return R.success();
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAllOs")
    public R<List<OsInfoDTO>> listAllOs() {
        return new R<>(controllerUtil.listAllOs());
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/getAllMenu")
    public R<MenuDTO> getAllMenu(MenuDTO menuDTO) {
        return new R<>(controllerUtil.getAllMenu(menuDTO));
    }

    /**
     * 权限分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPermissionPage")
    public R<Map<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        return new R<>(controllerUtil.getPermissionPage(permissionDTO));
    }

    /**
     * 根据系统编码获取所有菜单信息
     *
     * @param osCode 系统编码
     * @return 所有菜单
     */
    @GetMapping("/listMenuByOsCode")
    public R<List<MenuDTO>> listByOsCode(String osCode) {
        return new R<>(controllerUtil.listMenuByOsCode(osCode));
    }

    /**
     * 根据系统编码、角色编码、菜单编码获取所有权限
     *
     * @param roleCode 角色编码
     * @param osCode   系统编码
     * @param menuCode 菜单编码
     * @return 权限编码
     */
    @GetMapping("/getPermissionCodes")
    public R<List<String>> getPermissionCodes(String roleCode, String osCode, String menuCode) {
        return new R<>(controllerUtil.getPermissionCodes(roleCode, osCode, menuCode));
    }

    /**
     * 检查方法
     *
     * @param roleDTO 角色信息
     */
    @Override
    protected void check(RoleDTO roleDTO) {
        Assert.hasText(roleDTO.getName(), "角色名不能为空");
    }
}
