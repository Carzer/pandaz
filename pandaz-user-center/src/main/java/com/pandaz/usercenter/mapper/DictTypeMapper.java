package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.DictTypeEntity;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictTypeMapper extends BaseMapper<DictTypeEntity> {

    /**
     * 插入方法
     *
     * @param dictTypeEntity 字典类型信息
     * @return 结果
     */
    int insertSelective(DictTypeEntity dictTypeEntity);
}
