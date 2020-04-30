package com.pandaz.usercenter.controller;

import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.usercenter.OrganizationDTO;
import com.pandaz.commons.service.BaseService;
import com.pandaz.usercenter.entity.OrganizationEntity;
import com.pandaz.usercenter.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController extends BaseController<OrganizationDTO, OrganizationEntity> {

    /**
     * 组织服务
     */
    private final OrganizationService organizationService;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<OrganizationEntity> getBaseService() {
        return this.organizationService;
    }

    /**
     * 检查方法
     *
     * @param organizationDTO 组织信息
     */
    @Override
    protected void check(OrganizationDTO organizationDTO) {
        Assert.hasText(organizationDTO.getName(), "组织名称不能为空");
    }
}
