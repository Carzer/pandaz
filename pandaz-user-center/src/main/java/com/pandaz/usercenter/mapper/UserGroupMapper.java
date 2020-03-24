package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.UserGroupEntity;

/**
 * 用户-组关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface UserGroupMapper extends UcBaseMapper<UserGroupEntity> {
    /**
     * 逻辑删除
     *
     * @param userGroupEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByUserCode(UserGroupEntity userGroupEntity);

    /**
     * 逻辑删除
     *
     * @param userGroupEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByGroupCode(UserGroupEntity userGroupEntity);
}