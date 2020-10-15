package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.dto.MenuDTO;
import com.github.pandaz.auth.dto.OsInfoDTO;
import com.github.pandaz.auth.dto.PermissionDTO;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.service.MenuService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.annotations.security.PreAuth;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Menu", tags = "菜单信息")
@PreAuth("menu")
public class MenuController extends BaseController<MenuDTO, MenuEntity> {

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 工具类
     */
    private final ControllerUtil controllerUtil;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    @Override
    protected BaseService<MenuEntity> getBaseService() {
        return this.menuService;
    }

    /**
     * 权限分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @ApiOperation(value = "权限分页方法", notes = "权限分页方法")
    @GetMapping("/getPermissionPage")
    @PreAuthorize("hasAuth('{}/get')")
    public R<Map<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        return new R<>(controllerUtil.getPermissionPage(permissionDTO));
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @ApiOperation(value = "获取所有菜单", notes = "获取所有菜单")
    @GetMapping("/getAll")
    @PreAuthorize("hasAuth('{}/get')")
    public R<MenuDTO> getAll(MenuDTO menuDTO) {
        Assert.hasText(menuDTO.getOsCode(), "系统编码不能为空");
        return new R<>(controllerUtil.getAllMenu(menuDTO));
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @ApiOperation(value = "获取全部系统信息", notes = "获取全部系统信息")
    @GetMapping("/listAllOs")
    @PreAuthorize("hasAuth('{}/get')")
    public R<List<OsInfoDTO>> listAllOs() {
        return new R<>(controllerUtil.listAllOs());
    }

    /**
     * 检查方法
     *
     * @param menuDTO 菜单信息
     */
    @Override
    protected void check(MenuDTO menuDTO) {
        Assert.hasText(menuDTO.getName(), "菜单名称不能为空");
    }
}
