package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.PermissionDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
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
import java.util.HashMap;
import java.util.List;

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
    @GetMapping
    public ExecuteResult<PermissionDTO> get(@Valid PermissionDTO permissionDTO) {
        ExecuteResult<PermissionDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(permissionService.findByCode(permissionDTO.getCode()), PermissionDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param permissionDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPage")
    public ExecuteResult<HashMap<String, Object>> getPage(PermissionDTO permissionDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<PermissionEntity> page = permissionService.getPage(BeanCopyUtil.copy(permissionDTO, PermissionEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, PermissionDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param permissionDTO 权限信息
     * @return 权限信息
     */
    @PostMapping
    public ExecuteResult<PermissionDTO> insert(@RequestBody PermissionDTO permissionDTO, Principal principal) {
        ExecuteResult<PermissionDTO> result = new ExecuteResult<>();
        try {
            check(permissionDTO);
            PermissionEntity permissionEntity = BeanCopyUtil.copy(permissionDTO, PermissionEntity.class);
            permissionEntity.setId(UuidUtil.getId());
            permissionEntity.setCreatedBy(principal.getName());
            permissionEntity.setCreatedDate(LocalDateTime.now());
            Byte priority = permissionEntity.getPriority();
            permissionEntity.setBitResult(1 >> priority);
            permissionService.insert(permissionEntity);
            result.setData(BeanCopyUtil.copy(permissionEntity, permissionDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param permissionDTO 权限信息
     * @return 执行结果
     */
    @PutMapping
    public ExecuteResult<String> update(@Valid @RequestBody PermissionDTO permissionDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(permissionDTO);
            PermissionEntity permissionEntity = BeanCopyUtil.copy(permissionDTO, PermissionEntity.class);
            permissionEntity.setUpdatedBy(principal.getName());
            permissionEntity.setUpdatedDate(LocalDateTime.now());
            Byte priority = permissionEntity.getPriority();
            permissionEntity.setBitResult(1 >> priority);
            permissionService.updateByCode(permissionEntity);
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
     * @param codes 权限信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(permissionService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查方法
     *
     * @param permissionDTO 权限信息
     */
    private void check(PermissionDTO permissionDTO) {
        Assert.hasText(permissionDTO.getName(), "权限名称不能为空");
        Assert.hasText(permissionDTO.getOsCode(), "请关联系统信息");
        Assert.hasText(permissionDTO.getUrl(), "URL不能为空");
        Assert.notNull(permissionDTO.getRequestType(), "请求类型不能为空");
        Assert.notNull(permissionDTO.getPriority(), "优先级不能为空");
    }

}
