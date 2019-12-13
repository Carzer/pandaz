package com.pandaz.usercenter.service;

import com.pandaz.usercenter.entity.GroupEntity;

/**
 * pandaz:com.pandaz.usercenter.service
 * <p>
 * 组服务
 *
 * @author Carzer
 * @date 2019-10-25 10:58
 */
public interface GroupService {

    /**
     * 插入用户组
     *
     * @param group group
     * @return int
     * @author Carzer
     * @date 2019/10/25 11:01
     */
    int insert(GroupEntity group);

    /**
     * 根据编码删除
     *
     * @param groupCode groupCode
     * @return int
     * @author Carzer
     * @date 2019/10/25 15:57
     */
    int deleteByCode(String groupCode);
}
