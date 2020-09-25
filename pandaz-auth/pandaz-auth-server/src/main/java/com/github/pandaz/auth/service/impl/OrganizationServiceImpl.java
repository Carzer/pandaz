package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.OrganizationEntity;
import com.github.pandaz.auth.mapper.OrganizationMapper;
import com.github.pandaz.auth.service.OrganizationService;
import com.github.pandaz.auth.service.UserOrgService;
import com.github.pandaz.auth.util.CheckUtil;
import com.github.pandaz.commons.constants.CommonConstants;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织信息服务
 *
 * @author Carzer
 * @since 2019-12-13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, OrganizationEntity> implements OrganizationService {

    /**
     * 组织信息mapper
     */
    private final OrganizationMapper organizationMapper;

    /**
     * 用户-组织服务
     */
    private final UserOrgService userOrgService;

    /**
     * 根据编码查询
     *
     * @param organizationEntity 组织编码
     * @return 组织信息
     */
    @Override
    public OrganizationEntity findByCode(OrganizationEntity organizationEntity) {
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrganizationEntity::getCode, organizationEntity.getCode());
        return organizationMapper.selectOne(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    @Override
    public IPage<OrganizationEntity> getPage(OrganizationEntity organizationEntity) {
        Page<OrganizationEntity> page = new Page<>(organizationEntity.getPageNum(), organizationEntity.getPageSize());
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().likeRight(StringUtils.hasText(organizationEntity.getCode()), OrganizationEntity::getCode, organizationEntity.getCode());
        queryWrapper.lambda().likeRight(StringUtils.hasText(organizationEntity.getName()), OrganizationEntity::getName, organizationEntity.getName());
        queryWrapper.lambda().eq(organizationEntity.getLocked() != null, OrganizationEntity::getLocked, organizationEntity.getLocked());
        queryWrapper.lambda().ge(organizationEntity.getStartDate() != null, OrganizationEntity::getCreatedDate, organizationEntity.getStartDate());
        queryWrapper.lambda().le(organizationEntity.getEndDate() != null, OrganizationEntity::getCreatedDate, organizationEntity.getEndDate());
        queryWrapper.lambda().orderByDesc(OrganizationEntity::getCreatedDate);
        return page(page, queryWrapper);
    }

    /**
     * 插入方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    @Override
    public int insert(OrganizationEntity organizationEntity) {
        CheckUtil.checkOrSetCode(organizationMapper, organizationEntity, "组织编码重复");
        if (!StringUtils.hasText(organizationEntity.getId())) {
            organizationEntity.setId(UuidUtil.getId());
        }
        if (!StringUtils.hasText(organizationEntity.getParentCode())) {
            organizationEntity.setParentCode(CommonConstants.ROOT_CODE);
        }
        return organizationMapper.insertSelective(organizationEntity);
    }

    /**
     * 更新方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(OrganizationEntity organizationEntity) {
        UpdateWrapper<OrganizationEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(OrganizationEntity::getCode, organizationEntity.getCode());
        return organizationMapper.update(organizationEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(OrganizationEntity organizationEntity) {
        userOrgService.deleteByOrgCode(organizationEntity);
        return organizationMapper.logicDelete(organizationEntity);
    }

    /**
     * 批量删除
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            OrganizationEntity organizationEntity = new OrganizationEntity();
            organizationEntity.setCode(code);
            organizationEntity.setDeletedBy(deletedBy);
            organizationEntity.setDeletedDate(deletedDate);
            deleteByCode(organizationEntity);
        });
        clearMenuChildren(deletedBy, deletedDate, codes);
        return codes.size();
    }

    /**
     * 获取所有组织
     *
     * @param organizationEntity organizationEntity
     * @return 执行结果
     */
    @Override
    public List<OrganizationEntity> getAll(OrganizationEntity organizationEntity) {
        return organizationMapper.getAllAsTree(organizationEntity);
    }

    /**
     * 清理子组织
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     */
    @Async
    public void clearMenuChildren(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        try {
            log.debug("清理组织开始");
            codes.forEach(code -> {
                OrganizationEntity organizationEntity = new OrganizationEntity();
                organizationEntity.setDeletedBy(deletedBy);
                organizationEntity.setDeletedDate(deletedDate);
                organizationEntity.setCode(code);
                clearChildren(organizationEntity);
            });
            log.debug("清理组织结束");
        } catch (Exception e) {
            log.error("清理子组织异常：", e);
        }
    }

    /**
     * 清理子组织
     */
    private void clearChildren(OrganizationEntity organizationEntity) {
        List<OrganizationEntity> list = organizationMapper.selectByParentCode(organizationEntity.getCode());
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(organization -> {
                organization.setDeletedBy(organizationEntity.getDeletedBy());
                organization.setDeletedDate(organizationEntity.getDeletedDate());
                clearChildren(organization);
                deleteByCode(organization);
            });
        }
    }
}
