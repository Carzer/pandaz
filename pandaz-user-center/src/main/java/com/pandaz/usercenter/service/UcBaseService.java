package com.pandaz.usercenter.service;

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
public interface UcBaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 插入方法
     *
     * @param t 字典信息
     * @return 执行结果
     */
    int insert(T t);

    /**
     * 批量删除
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes);
}
