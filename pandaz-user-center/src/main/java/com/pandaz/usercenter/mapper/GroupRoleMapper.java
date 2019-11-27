package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 组-角色关系mapper
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Mapper
@Repository
public interface GroupRoleMapper extends BaseMapper<GroupRoleEntity> {

    /**
     * 插入关系
     *
     * @param groupRole 组-角色关系
     * @return int
     * @author Carzer
     * Date: 2019/10/23 16:58
     */
    int insert(GroupRoleEntity groupRole);

    /**
     * 插入关系
     *
     * @param groupRole 组-角色关系
     * @return int
     * @author Carzer
     * Date: 2019/10/23 16:58
     */
    int insertSelective(GroupRoleEntity groupRole);

    /**
     * 根据主键删除
     *
     * @param id 主键
     * @return int
     * @author Carzer
     * Date: 2019/10/25 15:26
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param groupCode 编码信息
     * @return int
     * @author Carzer
     * Date: 2019/10/25 15:27
     */
    int deleteByGroupCode(@Value("groupCode") String groupCode);

    /**
     * 根据编码删除
     *
     * @param roleCode 编码信息
     * @return int
     * @author Carzer
     * Date: 2019/10/25 15:27
     */
    int deleteByRoleCode(@Value("roleCode") String roleCode);

    /**
     * 根据组信息查询
     *
     * @param groupRole 组信息
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     * @author Carzer
     * Date: 2019/10/25 16:02
     */
    List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole);
}