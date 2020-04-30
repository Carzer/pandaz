package com.pandaz.commons.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.commons.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 基础服务
 *
 * @author Carzer
 * @since 2020-03-26
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 插入方法
     *
     * @param entity 插入方法
     * @return 执行结果
     */
    int insert(T entity);

    /**
     * 根据编码查询
     *
     * @param entity entity
     * @return 执行结果
     */
    default T findByCode(T entity) {
        return null;
    }

    /**
     * 获取分页信息
     *
     * @param entity 查询条件
     * @return 分页结果
     */
    default IPage<T> getPage(T entity) {
        Page<T> page = new Page<>(entity.getPageNum(), entity.getPageSize());
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        return page(page, queryWrapper);
    }

    /**
     * 根据编码更新
     *
     * @param entity entity
     * @return 执行结果
     */
    default int updateByCode(T entity) {
        return 0;
    }

    /**
     * 根据编码批量删除
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    default int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        return 0;
    }

    /**
     * 删除方法
     *
     * @param entity entity
     * @return 执行结果
     */
    default int deleteByCode(T entity) {
        return 0;
    }
}
