package com.pandaz.usercenter.controller;

import com.pandaz.commons.dto.usercenter.*;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.Result;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.service.RolePermissionService;
import com.pandaz.usercenter.service.RoleService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class RoleController {

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
    private final ControllerUtil<RoleService> controllerUtil;

    /**
     * 查询方法
     *
     * @param roleDTO 查询条件
     * @return 角色信息
     */
    @GetMapping(UrlConstants.GET)
    public Result<RoleDTO> get(@Valid RoleDTO roleDTO) {
        Result<RoleDTO> result = new Result<>();
        try {
            result.setData(BeanCopyUtil.copy(roleService.findByCode(roleDTO.getCode()), RoleDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "查询方法异常"));
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param roleDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public Result<Map<String, Object>> getPage(RoleDTO roleDTO) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            result.setData(controllerUtil.getRolePage(roleDTO));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param roleDTO 角色信息
     * @return 角色信息
     */
    @PostMapping(UrlConstants.INSERT)
    public Result<RoleDTO> insert(@RequestBody RoleDTO roleDTO, Principal principal) {
        Result<RoleDTO> result = new Result<>();
        check(roleDTO);
        try {
            RoleEntity roleEntity = BeanCopyUtil.copy(roleDTO, RoleEntity.class);
            roleEntity.setId(UuidUtil.getId());
            if (StringUtils.hasText(roleEntity.getCode())) {
                roleEntity.setCode(String.format("%s%s", SysConstants.ROLE_PREFIX, roleEntity.getCode()));
            }
            roleEntity.setCreatedBy(principal.getName());
            roleEntity.setCreatedDate(LocalDateTime.now());
            roleService.insert(roleEntity);
            result.setData(BeanCopyUtil.copy(roleEntity, roleDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "插入方法异常"));
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param roleDTO 角色信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public Result<String> update(@Valid @RequestBody RoleDTO roleDTO, Principal principal) {
        Result<String> result = new Result<>();
        check(roleDTO);
        try {
            RoleEntity roleEntity = BeanCopyUtil.copy(roleDTO, RoleEntity.class);
            roleEntity.setUpdatedBy(principal.getName());
            roleEntity.setUpdatedDate(LocalDateTime.now());
            roleService.updateByCode(roleEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "更新方法异常"));
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 角色信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public Result<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(roleService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 绑定权限
     *
     * @param rolePermissionDTO 权限信息
     * @param principal         当前用户
     * @return 执行结果
     */
    @PutMapping("/bindPermissions")
    public Result<String> bindPermissions(@RequestBody RolePermissionDTO rolePermissionDTO, Principal principal) {
        Result<String> result = new Result<>();
        try {
            RolePermissionEntity rolePermissionEntity = BeanCopyUtil.copy(rolePermissionDTO, RolePermissionEntity.class);
            rolePermissionService.bindPermissions(principal.getName(), LocalDateTime.now(), rolePermissionEntity);
            result.setData("绑定成功");
        } catch (Exception e) {
            log.error("绑定权限异常：", e);
            result.setError(controllerUtil.errorMsg(e, "绑定权限异常"));
        }
        return result;
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAllOs")
    public Result<List<OsInfoDTO>> listAllOs() {
        Result<List<OsInfoDTO>> result = new Result<>();
        try {
            result.setData(controllerUtil.listAllOs());
        } catch (Exception e) {
            log.error("获取全部系统信息异常：", e);
            result.setError(controllerUtil.errorMsg(e, "获取全部系统信息异常"));
        }
        return result;
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/getAllMenu")
    public Result<MenuDTO> getAllMenu(MenuDTO menuDTO) {
        Result<MenuDTO> result = new Result<>();
        try {
            result.setData(controllerUtil.getAllMenu(menuDTO, false));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
            result.setError(controllerUtil.errorMsg(e, "获取所有菜单异常"));
        }
        return result;
    }

    /**
     * 权限分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPermissionPage")
    public Result<Map<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            result.setData(controllerUtil.getPermissionPage(permissionDTO));
        } catch (Exception e) {
            log.error("权限分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "权限分页查询异常"));
        }
        return result;
    }

    /**
     * 根据系统编码获取所有菜单信息
     *
     * @param osCode 系统编码
     * @return 所有菜单
     */
    @GetMapping("/listMenuByOsCode")
    public Result<List<MenuDTO>> listByOsCode(String osCode) {
        Result<List<MenuDTO>> result = new Result<>();
        try {
            result.setData(controllerUtil.listMenuByOsCode(osCode));
        } catch (Exception e) {
            log.error("根据系统编码获取所有菜单信息异常：", e);
            result.setError(controllerUtil.errorMsg(e, "根据系统编码获取所有菜单信息异常"));
        }
        return result;
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
    public Result<List<String>> getPermissionCodes(String roleCode, String osCode, String menuCode) {
        Result<List<String>> result = new Result<>();
        try {
            result.setData(controllerUtil.getPermissionCodes(roleCode, osCode, menuCode));
        } catch (Exception e) {
            log.error("获取所有权限异常：", e);
            result.setError(controllerUtil.errorMsg(e, "获取所有权限异常"));
        }
        return result;
    }

    /**
     * 检查方法
     *
     * @param roleDTO 角色信息
     */
    private void check(RoleDTO roleDTO) {
        Assert.hasText(roleDTO.getName(), "角色名不能为空");
    }
}
