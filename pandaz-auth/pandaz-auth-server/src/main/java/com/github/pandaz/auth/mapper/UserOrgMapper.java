package com.github.pandaz.auth.mapper;

import com.github.pandaz.auth.entity.UserOrgEntity;
import com.github.pandaz.commons.mapper.BasisMapper;

import java.util.List;

/**
 * 用户-组织Mapper
 *
 * @author Carzer
 * @since 2020-05-21
 */
public interface UserOrgMapper extends BasisMapper<UserOrgEntity> {

    /**
     * 逻辑删除
     *
     * @param userOrgEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByUserCode(UserOrgEntity userOrgEntity);

    /**
     * 逻辑删除
     *
     * @param userOrgEntity 删除信息
     * @return 执行结果
     */
    int logicDeleteByOrgCode(UserOrgEntity userOrgEntity);

    /**
     * 列出组内成员
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindOrgMembers(UserOrgEntity userOrgEntity);

    /**
     * 列出用户所有的组
     *
     * @param userOrgEntity 查询条件
     * @return 执行结果
     */
    List<String> listBindUserOrg(UserOrgEntity userOrgEntity);

    /**
     * 批量插入
     *
     * @param list 成员关系
     * @return 执行结果
     */
    int batchInsert(List<UserOrgEntity> list);
}
