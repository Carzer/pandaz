package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.GroupEntity;

/**
 * 组mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface GroupMapper extends BaseMapper<GroupEntity> {

    /**
     * 插入方法
     *
     * @param group group
     * @return 插入结果
     */
    int insertSelective(GroupEntity group);

}