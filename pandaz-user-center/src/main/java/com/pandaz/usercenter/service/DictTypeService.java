package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.entity.DictTypeEntity;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictTypeService extends UcBaseService<DictTypeEntity> {

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

}
