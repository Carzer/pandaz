package com.pandaz.usercenter.service.impl;

import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.mapper.GroupRoleMapper;
import com.pandaz.usercenter.service.GroupRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 组-角色服务
 *
 * @author Carzer
 * @date 2019-11-05 17:37
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRoleServiceImpl implements GroupRoleService {

    /**
     * 组-角色mapper
     */
    private final GroupRoleMapper groupRoleMapper;

    /**
     * 插入方法
     *
     * @param groupRole groupRole
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:36
     */
    @Override
    public int insert(GroupRoleEntity groupRole) {
        return groupRoleMapper.insertSelective(groupRole);
    }

    /**
     * 根据组编码查询
     *
     * @param groupRole groupRole
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     * @author Carzer
     * @date 2019/11/5 17:36
     */
    @Override
    public List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole) {
        return groupRoleMapper.findByGroupCode(groupRole);
    }

    /**
     * 跟组编码删除
     *
     * @param groupCode groupCode
     * @return int
     * @author Carzer
     * @date 2019/11/5 17:37
     */
    @Override
    public int deleteByGroupCode(String groupCode) {
        return groupRoleMapper.deleteByGroupCode(groupCode);
    }
}
