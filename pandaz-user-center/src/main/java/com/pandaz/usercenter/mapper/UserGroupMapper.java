package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.UserGroupEntity;

/**
 * 用户-组关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserGroupMapper extends BaseMapper<UserGroupEntity> {
    /**
     * 插入方法
     *
     * @param userGroup userGroup
     * @return 插入结果
     */
    int insertSelective(UserGroupEntity userGroup);
}