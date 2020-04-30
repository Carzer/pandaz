package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.OrganizationEntity;
import com.pandaz.usercenter.mapper.OrganizationMapper;
import com.pandaz.usercenter.service.OrganizationService;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 编码检查工具
     */
    private final CheckUtil<OrganizationEntity, OrganizationMapper> checkUtil;

    /**
     * 根据编码查询
     *
     * @param organizationEntity 组织编码
     * @return 组织信息
     */
    @Override
    public OrganizationEntity findByCode(OrganizationEntity organizationEntity) {
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", organizationEntity.getCode());
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
        if (StringUtils.hasText(organizationEntity.getCode())) {
            queryWrapper.likeRight("code", organizationEntity.getCode());
        }
        if (StringUtils.hasText(organizationEntity.getName())) {
            queryWrapper.likeRight("name", organizationEntity.getName());
        }
        if (organizationEntity.getLocked() != null) {
            queryWrapper.eq("locked", organizationEntity.getLocked());
        }
        if (organizationEntity.getStartDate() != null) {
            queryWrapper.ge(SysConstants.CREATED_DATE_COLUMN, organizationEntity.getStartDate());
        }
        if (organizationEntity.getEndDate() != null) {
            queryWrapper.le(SysConstants.CREATED_DATE_COLUMN, organizationEntity.getEndDate());
        }
        queryWrapper.orderByDesc(SysConstants.CREATED_DATE_COLUMN);
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
        checkUtil.checkOrSetCode(organizationEntity, organizationMapper, "组织编码重复");
        if (!StringUtils.hasText(organizationEntity.getId())) {
            organizationEntity.setId(UuidUtil.getId());
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
        updateWrapper.eq("code", organizationEntity.getCode());
        return organizationMapper.update(organizationEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param organizationEntity 组织信息
     * @return 执行结果
     */
    @Override
    public int deleteByCode(OrganizationEntity organizationEntity) {
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
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return organizationMapper.batchLogicDelete(map);
    }
}
