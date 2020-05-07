package com.pandaz.auth.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pandaz.commons.mapper.BasisMapper;
import com.pandaz.auth.entity.DictInfoEntity;

/**
 * <p>
 * 字典信息 Mapper 接口
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictInfoMapper extends BasisMapper<DictInfoEntity> {

    /**
     * 显示typename
     *
     * @param code 编码
     * @return 字典信息
     */
    DictInfoEntity getWithTypeName(String code);

    /**
     * 分页方法，增加字典类型名称
     *
     * @param page   page
     * @param entity 分页信息
     * @return 执行结果
     */
    IPage<DictInfoEntity> getPageWithTypeName(Page<?> page, DictInfoEntity entity);
}
