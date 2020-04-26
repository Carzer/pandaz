package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.dto.usercenter.OsInfoDTO;
import com.pandaz.commons.dto.usercenter.PermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.service.PermissionService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 权限
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/permission")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionController {

    /**
     * 权限服务
     */
    private final PermissionService permissionService;

    /**
     * 工具类
     */
    private final ControllerUtil<PermissionService> controllerUtil;

    /**
     * 查询方法
     *
     * @param permissionDTO 查询条件
     * @return 权限信息
     */
    @GetMapping(UrlConstants.GET)
    public R<PermissionDTO> get(@Valid PermissionDTO permissionDTO) {
        PermissionDTO result = BeanCopyUtil.copy(permissionService.findByCode(permissionDTO.getCode()), PermissionDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(PermissionDTO permissionDTO) {
        IPage<PermissionEntity> page = permissionService.getPage(BeanCopyUtil.copy(permissionDTO, PermissionEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, PermissionDTO.class));
    }

    /**
     * 新增方法
     *
     * @param permissionDTO 权限信息
     * @return 权限信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<PermissionDTO> insert(@RequestBody PermissionDTO permissionDTO, Principal principal) {
        check(permissionDTO);
        PermissionEntity permissionEntity = BeanCopyUtil.copy(permissionDTO, PermissionEntity.class);
        permissionEntity.setId(UuidUtil.getId());
        permissionEntity.setCreatedBy(principal.getName());
        permissionEntity.setCreatedDate(LocalDateTime.now());
        permissionService.insert(permissionEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param permissionDTO 权限信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody PermissionDTO permissionDTO, Principal principal) {
        check(permissionDTO);
        PermissionEntity permissionEntity = BeanCopyUtil.copy(permissionDTO, PermissionEntity.class);
        permissionEntity.setUpdatedBy(principal.getName());
        permissionEntity.setUpdatedDate(LocalDateTime.now());
        permissionService.updateByCode(permissionEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 权限信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(permissionService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 获取全部系统信息
     *
     * @return 系统信息
     */
    @GetMapping("/listAllOs")
    public R<List<OsInfoDTO>> listAll() {
        return new R<>(controllerUtil.listAllOs());
    }

    /**
     * 根据系统编码获取所有菜单信息
     *
     * @return 所有菜单
     */
    @GetMapping("/listMenuByOsCode")
    public R<List<MenuDTO>> listByOsCode(String osCode) {
        return new R<>(controllerUtil.listMenuByOsCode(osCode));
    }

    /**
     * 检查方法
     *
     * @param permissionDTO 权限信息
     */
    private void check(PermissionDTO permissionDTO) {
        Assert.hasText(permissionDTO.getName(), "权限名称不能为空");
    }
}
