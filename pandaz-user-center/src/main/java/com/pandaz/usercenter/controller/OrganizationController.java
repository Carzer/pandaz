package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.OrganizationDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.OrganizationEntity;
import com.pandaz.usercenter.service.OrganizationService;
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
     * 工具类
     */
    private final ControllerUtil<OrganizationService> controllerUtil;

    /**
     * 查询方法
     *
     * @param organizationDTO 查询条件
     * @return 组织信息
     */
    @GetMapping(UrlConstants.GET)
    public R<OrganizationDTO> get(@Valid OrganizationDTO organizationDTO) {
        OrganizationDTO result = BeanCopyUtil.copy(organizationService.findByCode(organizationDTO.getCode()), OrganizationDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param organizationDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(OrganizationDTO organizationDTO) {
        IPage<OrganizationEntity> page = organizationService.getPage(BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, OrganizationDTO.class));
    }

    /**
     * 新增方法
     *
     * @param organizationDTO 组织信息
     * @return 组织信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<String> insert(@RequestBody OrganizationDTO organizationDTO, Principal principal) {
        check(organizationDTO);
        OrganizationEntity organizationEntity = BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class);
        organizationEntity.setId(UuidUtil.getId());
        organizationEntity.setCreatedBy(principal.getName());
        organizationEntity.setCreatedDate(LocalDateTime.now());
        organizationService.insert(organizationEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param organizationDTO 组织信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody OrganizationDTO organizationDTO, Principal principal) {
        check(organizationDTO);
        OrganizationEntity organizationEntity = BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class);
        organizationEntity.setUpdatedBy(principal.getName());
        organizationEntity.setUpdatedDate(LocalDateTime.now());
        organizationService.updateByCode(organizationEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 组织信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(organizationService, principal.getName(), LocalDateTime.now(), codes);
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
