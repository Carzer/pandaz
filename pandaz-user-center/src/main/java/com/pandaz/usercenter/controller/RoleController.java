package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.*;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public ExecuteResult<RoleDTO> get(@Valid RoleDTO roleDTO) {
        ExecuteResult<RoleDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(roleService.findByCode(roleDTO.getCode()), RoleDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<HashMap<String, Object>> getPage(RoleDTO roleDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<RoleEntity> page = roleService.getPage(BeanCopyUtil.copy(roleDTO, RoleEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, RoleDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<RoleDTO> insert(@RequestBody RoleDTO roleDTO, Principal principal) {
        ExecuteResult<RoleDTO> result = new ExecuteResult<>();
        try {
            check(roleDTO);
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
            result.setError(e.getMessage());
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
    public ExecuteResult<String> update(@Valid @RequestBody RoleDTO roleDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(roleDTO);
            RoleEntity roleEntity = BeanCopyUtil.copy(roleDTO, RoleEntity.class);
            roleEntity.setUpdatedBy(principal.getName());
            roleEntity.setUpdatedDate(LocalDateTime.now());
            roleService.updateByCode(roleEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(roleService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 绑定权限
     *
     * @param rolePermissionDTO 权限信息
     * @param principal         当前用户
     * @return 执行结果
     */
    @PostMapping("/bindPermissions")
    public ExecuteResult<String> bindPermissions(@RequestBody RolePermissionDTO rolePermissionDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            RolePermissionEntity rolePermissionEntity = BeanCopyUtil.copy(rolePermissionDTO, RolePermissionEntity.class);
            rolePermissionService.bindPermissions(principal.getName(), LocalDateTime.now(), rolePermissionEntity);
            result.setData("绑定成功");
        } catch (Exception e) {
            log.error("绑定权限异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAllOs")
    public ExecuteResult<ArrayList<OsInfoDTO>> listAllOs() {
        ExecuteResult<ArrayList<OsInfoDTO>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.listAllOs());
        } catch (Exception e) {
            log.error("获取全部系统信息异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 获取所有菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/getAllMenu")
    public ExecuteResult<MenuDTO> getAllMenu(MenuDTO menuDTO) {
        ExecuteResult<MenuDTO> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.getAllMenu(menuDTO));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<HashMap<String, Object>> getPermissionPage(PermissionDTO permissionDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.getPermissionPage(permissionDTO));
        } catch (Exception e) {
            log.error("权限分页查询异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<ArrayList<MenuDTO>> listByOsCode(String osCode) {
        ExecuteResult<ArrayList<MenuDTO>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.listMenuByOsCode(osCode));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
            result.setError(e.getMessage());
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
    public ExecuteResult<ArrayList<String>> getPermissionCodes(String roleCode, String osCode, String menuCode) {
        ExecuteResult<ArrayList<String>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.getPermissionCodes(roleCode, osCode, menuCode));
        } catch (Exception e) {
            log.error("获取所有菜单异常：", e);
            result.setError(e.getMessage());
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
