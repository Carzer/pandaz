package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.OrganizationDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.OrganizationEntity;
import com.pandaz.usercenter.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 组织
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/organization")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController {

    /**
     * 组织服务
     */
    private final OrganizationService organizationService;

    /**
     * 查询方法
     *
     * @param organizationDTO 查询条件
     * @return 组织信息
     */
    @GetMapping
    public ExecuteResult<OrganizationDTO> get(@Valid OrganizationDTO organizationDTO) {
        ExecuteResult<OrganizationDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(organizationService.findByCode(organizationDTO.getCode()), OrganizationDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param organizationDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPage")
    public ExecuteResult<HashMap<String, Object>> getPage(OrganizationDTO organizationDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<OrganizationEntity> page = organizationService.getPage(BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, OrganizationDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param organizationDTO 组织信息
     * @return 组织信息
     */
    @PostMapping
    public ExecuteResult<OrganizationDTO> insert(@RequestBody OrganizationDTO organizationDTO, Principal principal) {
        ExecuteResult<OrganizationDTO> result = new ExecuteResult<>();
        try {
            check(organizationDTO);
            OrganizationEntity organizationEntity = BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class);
            organizationEntity.setId(UuidUtil.getId());
            organizationEntity.setCreatedBy(principal.getName());
            organizationEntity.setCreatedDate(LocalDateTime.now());
            organizationService.insert(organizationEntity);
            result.setData(BeanCopyUtil.copy(organizationEntity, organizationDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param organizationDTO 组织信息
     * @return 执行结果
     */
    @PutMapping
    public ExecuteResult<String> update(@Valid @RequestBody OrganizationDTO organizationDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(organizationDTO);
            OrganizationEntity organizationEntity = BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class);
            organizationEntity.setUpdatedBy(principal.getName());
            organizationEntity.setUpdatedDate(LocalDateTime.now());
            organizationService.updateByCode(organizationEntity);
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
     * @param organizationDTO 组织信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@Valid @RequestBody OrganizationDTO organizationDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            organizationDTO.setDeletedBy(principal.getName());
            organizationDTO.setDeletedDate(LocalDateTime.now());
            organizationService.deleteByCode(BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class));
            result.setData("删除成功");
        } catch (Exception e) {
            log.error("删除方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 检查方法
     *
     * @param organizationDTO 组织信息
     */
    private void check(OrganizationDTO organizationDTO) {
        Assert.hasText(organizationDTO.getName(), "组织名称不能为空");
    }
}