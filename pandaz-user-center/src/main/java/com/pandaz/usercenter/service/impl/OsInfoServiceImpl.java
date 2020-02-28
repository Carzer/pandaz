package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.OsInfoEntity;
import com.pandaz.usercenter.mapper.OsInfoMapper;
import com.pandaz.usercenter.service.OsInfoService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
     * 编码检查工具
     */
    private final CheckUtils<OsInfoEntity, OsInfoMapper> checkUtils;

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return com.pandaz.usercenter.entity.OsInfoEntity
     */
    @Override
    public OsInfoEntity insert(OsInfoEntity osInfo) {
        checkUtils.checkOrSetCode(osInfo, osInfoMapper, "系统编码已存在");
        if (!StringUtils.hasText(osInfo.getId())) {
            osInfo.setId(UuidUtil.getId());
        }
        osInfoMapper.insertSelective(osInfo);
        return osInfo;
    }

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 查询结果
     */
    @Override
    public OsInfoEntity findByCode(String code) {
        QueryWrapper<OsInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
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
    public int deleteByCode(OsInfoEntity osInfoEntity) {
        UpdateWrapper<OsInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", osInfoEntity.getCode());
        return osInfoMapper.delete(updateWrapper);
    }
}
