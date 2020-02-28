package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.DictInfoEntity;

/**
 * <p>
 * 字典信息表 服务类
 * </p>
 *
 * @author Carzer
 * @since 2019-12-19
 */
public interface DictInfoService extends IService<DictInfoEntity> {

    /**
     * 查询方法
     *
     * @param code 编码
     * @return 结果
     */
    DictInfoEntity findByCode(String code);

    /**
     * 分页方法
     *
     * @param dictInfoEntity 查询条件
     * @return 分页
     */
    IPage<DictInfoEntity> getPage(DictInfoEntity dictInfoEntity);

    /**
     * 插入方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    int insert(DictInfoEntity dictInfoEntity);

    /**
     * 更新方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    int updateByCode(DictInfoEntity dictInfoEntity);

    /**
     * 删除方法
     *
     * @param dictInfoEntity 字典信息
     * @return 执行结果
     */
    int deleteByCode(DictInfoEntity dictInfoEntity);
}
