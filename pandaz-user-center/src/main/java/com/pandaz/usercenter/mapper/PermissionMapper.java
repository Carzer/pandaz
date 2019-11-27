package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 权限mapper
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Repository
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 插入方法
     *
     * @param permission permission
     * @return int
     * @author Carzer
     * Date: 2019/11/20 10:14
     */
    int insert(PermissionEntity permission);

    /**
     * 插入方法
     *
     * @param permission permission
     * @return int
     * @author Carzer
     * Date: 2019/11/20 10:14
     */
    int insertSelective(PermissionEntity permission);

}