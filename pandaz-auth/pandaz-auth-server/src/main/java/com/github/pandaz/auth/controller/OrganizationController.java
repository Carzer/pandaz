package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.auth.service.OrganizationService;
import com.github.pandaz.auth.service.UserOrgService;
import com.github.pandaz.commons.constants.CommonConstants;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.OrganizationDTO;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
     * 用户-组织服务
     */
    private final UserOrgService userOrgService;

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
     * 获取所有组织
     *
     * @return 所有组织
     */
    @ApiOperation(value = "获取所有组织", notes = "获取所有组织")
    @GetMapping("/getAll")
    public R<OrganizationDTO> getAll(OrganizationDTO organizationDTO) {
        OrganizationEntity organizationEntity = BeanCopyUtil.copy(organizationDTO, OrganizationEntity.class);
        organizationEntity.setParentCode(CommonConstants.ROOT_CODE);
        List<OrganizationEntity> list = organizationService.getAll(organizationEntity);
        organizationEntity.setChildren(list);
        return new R<>(transferToDTO(organizationEntity));
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

    /**
     * 将entity及children转换为dto
     *
     * @param organizationEntity entity
     * @return dto
     */
    private OrganizationDTO transferToDTO(OrganizationEntity organizationEntity) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        List<OrganizationEntity> entityList = organizationEntity.getChildren();
        if (!CollectionUtils.isEmpty(entityList)) {
            List<OrganizationDTO> dtoList = new ArrayList<>();
            entityList.forEach(organization -> dtoList.add(transferToDTO(organization)));
            organizationDTO.setChildren(dtoList);
        }
        organizationDTO.setId(organizationEntity.getId());
        organizationDTO.setCode(organizationEntity.getCode());
        organizationDTO.setParentCode(organizationEntity.getParentCode());
        organizationDTO.setName(organizationEntity.getName());
        organizationDTO.setRemark(organizationEntity.getRemark());
        organizationDTO.setLocked(organizationEntity.getLocked());
        organizationDTO.setSorting(organizationEntity.getSorting());
        return organizationDTO;
    }
}
