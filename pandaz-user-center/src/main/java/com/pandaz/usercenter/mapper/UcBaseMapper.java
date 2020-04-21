package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.commons.entity.BaseEntity;

import java.util.Map;

/**
 * base mapper
 *
 * @author Carzer
 * @since 2020-03-24
 */
public interface UcBaseMapper<T extends BaseEntity> extends BaseMapper<T> {

    /**
     * 插入方法
     *
     * @param t 插入信息
     * @return 执行结果
     */
    int insertSelective(T t);

    /**
     * 逻辑删除
     *
     * @param t 删除信息
     * @return 执行结果
     */
    int logicDelete(T t);

    /**
     * 批量逻辑删除
     *
     * @param map 删除列表
     * @return 执行结果
     */
    int batchLogicDelete(Map<String, Object> map);
}
