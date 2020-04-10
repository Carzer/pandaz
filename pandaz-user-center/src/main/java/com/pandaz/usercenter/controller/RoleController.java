package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.RoleDTO;
import com.pandaz.commons.dto.usercenter.RolePermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
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
    @GetMapping
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
    @GetMapping("/getPage")
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
    @PostMapping
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
    @PutMapping
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
    @DeleteMapping
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
    @PostMapping("/bindPermission")
    public ExecuteResult<String> bindPermission(@RequestBody RolePermissionDTO rolePermissionDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            List<PermissionEntity> permissionEntityList = BeanCopyUtil.copyList(rolePermissionDTO.getPermissions(), PermissionEntity.class);
            rolePermissionService.bindPermission(principal.getName(), LocalDateTime.now(), rolePermissionDTO.getRoleCode(), permissionEntityList);
            result.setData("绑定成功");
        } catch (Exception e) {
            log.error("绑定权限异常：", e);
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
