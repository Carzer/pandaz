package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.dto.MenuDTO;
import com.github.pandaz.auth.dto.OsInfoDTO;
import com.github.pandaz.auth.dto.PermissionDTO;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.auth.service.PermissionService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/permission")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Permission", tags = "权限信息")
public class PermissionController extends BaseController<PermissionDTO, PermissionEntity> {

    /**
     * 权限服务
     */
    private final PermissionService permissionService;

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
    protected BaseService<PermissionEntity> getBaseService() {
        return this.permissionService;
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @ApiOperation(value = "获取全部系统信息", notes = "获取全部系统信息")
    @GetMapping("/listAllOs")
    public R<List<OsInfoDTO>> listAll() {
        return new R<>(controllerUtil.listAllOs());
    }

    /**
     * 根据系统编码获取所有菜单信息
     *
     * @return 所有菜单
     */
    @ApiOperation(value = "获取所有菜单信息", notes = "根据系统编码获取所有菜单信息")
    @GetMapping("/listMenuByOsCode")
    public R<List<MenuDTO>> listByOsCode(String osCode) {
        return new R<>(controllerUtil.listMenuByOsCode(osCode));
    }

    /**
     * 检查方法
     *
     * @param permissionDTO 权限信息
     */
    @Override
    protected void check(PermissionDTO permissionDTO) {
        Assert.hasText(permissionDTO.getName(), "权限名称不能为空");
    }
}
