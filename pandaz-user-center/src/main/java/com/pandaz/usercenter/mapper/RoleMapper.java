package com.pandaz.usercenter.mapper;

import com.pandaz.commons.BaseMapper;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.mapper
 * <p>
 * 角色mapper
 *
 * @author Carzer
 * Date: 2019-10-23 10:52
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 插入方法
     *
     * @param role 角色
     * @return int
     * @author Carzer
     * Date: 2019/10/23 16:05
     */
    int insert(RoleEntity role);

    /**
     * 插入方法
     *
     * @param role 角色
     * @return int
     * @author Carzer
     * Date: 2019/10/23 16:05
     */
    int insertSelective(RoleEntity role);

    /**
     * 根据用户编码获取非私有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * Date: 2019/10/25 09:29
     */
    List<RoleDetailEntity> getPublicRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取私有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * Date: 2019/10/25 09:29
     */
    List<RoleDetailEntity> getPrivateRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取所有角色信息
     *
     * @param userCode 用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * Date: 2019/10/25 09:29
     */
    List<RoleDetailEntity> getAllRoles(@Value("userCode") String userCode);

    /**
     * 删除方法
     *
     * @param id id
     * @return int
     * @author Carzer
     * Date: 2019/9/9 09:21
     */
    int deleteByPrimaryKey(@Value("id") String id);

    /**
     * 根据编码删除
     *
     * @param code code
     * @return int
     * @author Carzer
     * Date: 2019/10/25 15:43
     */
    int deleteByCode(@Value("code") String code);

    /**
     * 更新方法
     *
     * @param role role
     * @return int
     * @author Carzer
     * Date: 2019/9/9 10:06
     */
    int updateByPrimaryKeySelective(RoleEntity role);

    /**
     * 更新方法
     *
     * @param role role
     * @return int
     * @author Carzer
     * Date: 2019/9/9 10:06
     */
    int updateByPrimaryKey(RoleEntity role);

}