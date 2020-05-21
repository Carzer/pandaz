package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.auth.service.OrganizationService;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.OrganizationDTO;
import com.github.pandaz.commons.service.BaseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Organization", tags = "组织信息")
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
