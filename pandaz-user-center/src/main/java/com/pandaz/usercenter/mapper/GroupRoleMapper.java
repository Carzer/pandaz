package com.pandaz.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 组-角色关系mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface GroupRoleMapper extends BaseMapper<GroupRoleEntity> {

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param groupCode 编码信息
     * @return int
     */
    int deleteByGroupCode(@Value("groupCode") String groupCode);

    /**
     * 根据编码删除
     *
     * @param roleCode 编码信息
     * @return int
     */
    int deleteByRoleCode(@Value("roleCode") String roleCode);

    /**
     * 根据组信息查询
     *
     * @param groupRole 组信息
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     */
    List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole);

    /**
     * 插入方法
     *
     * @param groupRole groupRole
     * @return 插入结果
     */
    int insertSelective(GroupRoleEntity groupRole);
}