package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.DictTypeEntity;
import com.github.pandaz.auth.mapper.DictTypeMapper;
import com.github.pandaz.auth.service.DictTypeService;
import com.github.pandaz.auth.util.CheckUtil;
import com.github.pandaz.commons.util.UuidUtil;
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
     * @param dictTypeEntity 编码
     * @return 字典类型
     */
    @Override
    public DictTypeEntity findByCode(DictTypeEntity dictTypeEntity) {
        QueryWrapper<DictTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictTypeEntity::getCode, dictTypeEntity.getCode());
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
        queryWrapper.lambda().likeRight(StringUtils.hasText(dictTypeEntity.getName()), DictTypeEntity::getName, dictTypeEntity.getName());
        queryWrapper.lambda().likeRight(StringUtils.hasText(dictTypeEntity.getCode()), DictTypeEntity::getCode, dictTypeEntity.getCode());
        queryWrapper.lambda().eq(dictTypeEntity.getLocked() != null, DictTypeEntity::getLocked, dictTypeEntity.getLocked());
        queryWrapper.lambda().ge(dictTypeEntity.getStartDate() != null, DictTypeEntity::getCreatedDate, dictTypeEntity.getStartDate());
        queryWrapper.lambda().le(dictTypeEntity.getEndDate() != null, DictTypeEntity::getCreatedDate, dictTypeEntity.getEndDate());
        queryWrapper.lambda().orderByDesc(DictTypeEntity::getStartDate);
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
        checkUtil.checkOrSetCode(dictTypeEntity, dictTypeMapper, "字典信息编码重复");
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
        updateWrapper.lambda().eq(DictTypeEntity::getCode, dictTypeEntity.getCode());
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
        return dictTypeMapper.batchLogicDelete(map);
    }
}
