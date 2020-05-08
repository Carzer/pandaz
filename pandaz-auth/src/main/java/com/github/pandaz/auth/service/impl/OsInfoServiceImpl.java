package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.service.MenuService;
import com.github.pandaz.commons.util.UuidUtil;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.entity.OsInfoEntity;
import com.github.pandaz.auth.mapper.OsInfoMapper;
import com.github.pandaz.auth.service.OsInfoService;
import com.github.pandaz.auth.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统信息服务
 *
 * @author Carzer
 * @since 2019-11-01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OsInfoServiceImpl extends ServiceImpl<OsInfoMapper, OsInfoEntity> implements OsInfoService {

    /**
     * 系统信息mapper
     */
    private final OsInfoMapper osInfoMapper;

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 编码检查工具
     */
    private final CheckUtil<OsInfoEntity, OsInfoMapper> checkUtil;

    /**
     * 插入方法
     *
     * @param osInfoEntity osInfo
     * @return 执行结果
     */
    @Override
    public int insert(OsInfoEntity osInfoEntity) {
        checkUtil.checkOrSetCode(osInfoEntity, osInfoMapper, "系统编码重复");
        if (!StringUtils.hasText(osInfoEntity.getId())) {
            osInfoEntity.setId(UuidUtil.getId());
        }
        return osInfoMapper.insertSelective(osInfoEntity);
    }

    /**
     * 根据编码查询
     *
     * @param osInfoEntity 编码
     * @return 查询结果
     */
    @Override
    public OsInfoEntity findByCode(OsInfoEntity osInfoEntity) {
        QueryWrapper<OsInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", osInfoEntity.getCode());
        return osInfoMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param osInfoEntity 系统信息
     * @return 分页结果
     */
    @Override
    public IPage<OsInfoEntity> getPage(OsInfoEntity osInfoEntity) {
        Page<OsInfoEntity> page = new Page<>(osInfoEntity.getPageNum(), osInfoEntity.getPageSize());
        QueryWrapper<OsInfoEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(osInfoEntity.getCode())) {
            queryWrapper.likeRight("code", osInfoEntity.getCode());
        }
        if (StringUtils.hasText(osInfoEntity.getName())) {
            queryWrapper.likeRight("name", osInfoEntity.getName());
        }
        if (osInfoEntity.getLocked() != null) {
            queryWrapper.eq("locked", osInfoEntity.getLocked());
        }
        if (osInfoEntity.getStartDate() != null) {
            queryWrapper.ge(SysConstants.CREATED_DATE_COLUMN, osInfoEntity.getStartDate());
        }
        if (osInfoEntity.getEndDate() != null) {
            queryWrapper.le(SysConstants.CREATED_DATE_COLUMN, osInfoEntity.getEndDate());
        }
        queryWrapper.orderByDesc(SysConstants.CREATED_DATE_COLUMN);
        return page(page, queryWrapper);
    }

    /**
     * 更新方法
     *
     * @param osInfoEntity 系统信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(OsInfoEntity osInfoEntity) {
        UpdateWrapper<OsInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", osInfoEntity.getCode());
        return osInfoMapper.update(osInfoEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param osInfoEntity 系统信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(OsInfoEntity osInfoEntity) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setOsCode(osInfoEntity.getCode());
        menuEntity.setDeletedBy(osInfoEntity.getDeletedBy());
        menuEntity.setDeletedDate(osInfoEntity.getDeletedDate());
        menuService.deleteByOsCode(menuEntity);
        return osInfoMapper.logicDelete(osInfoEntity);
    }

    /**
     * 批量删除系统信息
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
            OsInfoEntity osInfoEntity = new OsInfoEntity();
            osInfoEntity.setCode(code);
            osInfoEntity.setDeletedBy(deletedBy);
            osInfoEntity.setDeletedDate(deletedDate);
            deleteByCode(osInfoEntity);
        });
        return codes.size();
    }
}
