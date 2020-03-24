package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.DictTypeEntity;
import com.pandaz.usercenter.mapper.DictTypeMapper;
import com.pandaz.usercenter.service.DictTypeService;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictTypeEntity> implements DictTypeService {

    /**
     * 字典类型mapper
     */
    private final DictTypeMapper dictTypeMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtil<DictTypeEntity, DictTypeMapper> checkUtil;

    /**
     * 查询方法
     *
     * @param code 编码
     * @return 字典类型
     */
    @Override
    public DictTypeEntity findByCode(String code) {
        QueryWrapper<DictTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return dictTypeMapper.selectOne(queryWrapper);
    }

    /**
     * 分页分页方法
     *
     * @param dictTypeEntity 查询条件
     * @return 分页
     */
    @Override
    public IPage<DictTypeEntity> getPage(DictTypeEntity dictTypeEntity) {
        Page<DictTypeEntity> page = new Page<>(dictTypeEntity.getPageNum(), dictTypeEntity.getPageSize());
        QueryWrapper<DictTypeEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(dictTypeEntity.getName())) {
            queryWrapper.likeRight("name", dictTypeEntity.getName());
        }
        if (StringUtils.hasText(dictTypeEntity.getCode())) {
            queryWrapper.likeRight("code", dictTypeEntity.getCode());
        }
        return page(page, queryWrapper);
    }

    /**
     * 插入方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    @Override
    public int insert(DictTypeEntity dictTypeEntity) {
        checkUtil.checkOrSetCode(dictTypeEntity, dictTypeMapper, "字典信息编码已存在");
        if (!StringUtils.hasText(dictTypeEntity.getId())) {
            dictTypeEntity.setId(UuidUtil.getId());
        }
        return dictTypeMapper.insertSelective(dictTypeEntity);
    }

    /**
     * 更新方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    @Override
    public int updateByCode(DictTypeEntity dictTypeEntity) {
        UpdateWrapper<DictTypeEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", dictTypeEntity.getCode());
        return dictTypeMapper.update(dictTypeEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    @Override
    public int deleteByCode(DictTypeEntity dictTypeEntity) {
        return dictTypeMapper.logicDelete(dictTypeEntity);
    }
}
