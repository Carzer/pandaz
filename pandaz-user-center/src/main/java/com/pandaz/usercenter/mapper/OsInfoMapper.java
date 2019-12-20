package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.OsInfoEntity;

/**
 * 菜单mapper
 *
 * @author Carzer
 * @since 2019-11-01
 */
public interface OsInfoMapper extends BaseMapper<OsInfoEntity> {

    /**
     * 插入方法
     *
     * @param osInfo osInfo
     * @return 插入结果
     */
    int insertSelective(OsInfoEntity osInfo);
}