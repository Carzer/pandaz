package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 角色-权限mapper
 *
 * @author Carzer
 * @date 2019-10-23 10:52
 */
@Repository
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return int
     * @author Carzer
     * @date 2019/11/20 10:14
     */
    int insert(RolePermissionEntity rolePermission);

    /**
     * 插入方法
     *
     * @param rolePermission rolePermission
     * @return int
     * @author Carzer
     * @date 2019/11/20 10:14
     */
    int insertSelective(RolePermissionEntity rolePermission);
}