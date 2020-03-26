package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.usercenter.entity.GroupEntity;

/**
 * 组服务
 *
 * @author Carzer
 * @since 2019-10-25 10:58
 */
public interface GroupService extends UcBaseService<GroupEntity> {

    /**
     * 根据编码删除
     *
     * @param groupEntity 组信息
     * @return int
     */
    int deleteByCode(GroupEntity groupEntity);

    /**
     * 根据编码删除
     *
     * @param code 组编码
     * @return 查询结果
     */
    GroupEntity findByCode(String code);

    /**
     * 分页方法
     *
     * @param groupEntity 查询信息
     * @return 查询结果
     */
    IPage<GroupEntity> getPage(GroupEntity groupEntity);

    /**
     * 更新方法
     *
     * @param groupEntity 组信息
     * @return 执行结果
     */
    int updateByCode(GroupEntity groupEntity);

}
