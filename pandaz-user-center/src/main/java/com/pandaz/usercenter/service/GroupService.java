package com.pandaz.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaz.usercenter.entity.GroupEntity;

/**
 * 组服务
 *
 * @author Carzer
 * @since 2019-10-25 10:58
 */
public interface GroupService extends IService<GroupEntity> {

    /**
     * 插入用户组
     *
     * @param group group
     * @return int
     */
    int insert(GroupEntity group);

    /**
     * 根据编码删除
     *
     * @param groupCode groupCode
     * @return int
     */
    int deleteByCode(String groupCode);
}
