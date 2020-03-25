package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.DictTypeEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictTypeService extends IService<DictTypeEntity> {

    /**
     * 查询方法
     *
     * @param code 编码
     * @return 字典类型
     */
    DictTypeEntity findByCode(String code);

    /**
     * 分页分页方法
     *
     * @param dictTypeEntity 查询条件
     * @return 分页
     */
    IPage<DictTypeEntity> getPage(DictTypeEntity dictTypeEntity);

    /**
     * 插入方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    int insert(DictTypeEntity dictTypeEntity);

    /**
     * 更新方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    int updateByCode(DictTypeEntity dictTypeEntity);

    /**
     * 删除方法
     *
     * @param dictTypeEntity 字典类型
     * @return 执行结果
     */
    int deleteByCode(DictTypeEntity dictTypeEntity);

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
