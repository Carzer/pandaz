package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.DictInfoEntity;

/**
 * <p>
 * 字典信息表 Mapper 接口
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictInfoMapper extends BaseMapper<DictInfoEntity> {

    /**
     * 插入方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    int insertSelective(DictInfoEntity dictInfoEntity);
}
