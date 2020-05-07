package com.pandaz.auth.service;

import com.pandaz.commons.service.BaseService;
import com.pandaz.auth.entity.DictInfoEntity;

/**
 * <p>
 * 字典信息表 服务类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictInfoService extends BaseService<DictInfoEntity> {

    /**
     * 查询方法
     *
     * @param code 编码
     * @return 结果
     */
    DictInfoEntity getWithTypeName(String code);
}
