package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.DictInfoEntity;
import com.github.pandaz.auth.mapper.DictInfoMapper;
import com.github.pandaz.auth.service.DictInfoService;
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
 * 字典信息表 服务实现类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictInfoServiceImpl extends ServiceImpl<DictInfoMapper, DictInfoEntity> implements DictInfoService {

    /**
     * 字典信息mapper
     */
    private final DictInfoMapper dictInfoMapper;

    /**
     * 查询方法
     *
     * @param dictInfoEntity 编码
     * @return 结果
     */
    @Override
    public DictInfoEntity findByCode(DictInfoEntity dictInfoEntity) {
        QueryWrapper<DictInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictInfoEntity::getCode, dictInfoEntity.getCode());
        return dictInfoMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param dictInfoEntity 查询条件
     * @return 分页
     */
    @Override
    public IPage<DictInfoEntity> getPage(DictInfoEntity dictInfoEntity) {
        Page<DictInfoEntity> page = new Page<>(dictInfoEntity.getPageNum(), dictInfoEntity.getPageSize());
        return dictInfoMapper.getPageWithTypeName(page, dictInfoEntity);
    }

    /**
     * 插入方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    @Override
    public int insert(DictInfoEntity dictInfoEntity) {
        CheckUtil.checkOrSetCode(dictInfoMapper, dictInfoEntity, "字典信息编码重复");
        if (!StringUtils.hasText(dictInfoEntity.getId())) {
            dictInfoEntity.setId(UuidUtil.getId());
        }
        return dictInfoMapper.insertSelective(dictInfoEntity);
    }

    /**
     * 更新方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(DictInfoEntity dictInfoEntity) {
        UpdateWrapper<DictInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(DictInfoEntity::getCode, dictInfoEntity.getCode());
        return dictInfoMapper.update(dictInfoEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    @Override
    public int logicDeleteByCode(DictInfoEntity dictInfoEntity) {
        return dictInfoMapper.logicDeleteByCode(dictInfoEntity);
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
    public int logicDeleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return dictInfoMapper.logicDeleteByCodes(map);
    }

    /**
     * 查询方法
     *
     * @param code 编码
     * @return 结果
     */
    @Override
    public DictInfoEntity getWithTypeName(String code) {
        return dictInfoMapper.getWithTypeName(code);
    }
}
