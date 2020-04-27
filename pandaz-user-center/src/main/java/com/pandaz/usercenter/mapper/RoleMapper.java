package com.pandaz.usercenter.mapper;

import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 角色mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RoleMapper extends UcBaseMapper<RoleEntity> {

    /**
     * 根据用户编码获取非私有角色信息
     *
     * @param userCode 用户编码
     * @return 执行结果
     */
    List<RoleDetailEntity> getPublicRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取私有角色信息
     *
     * @param userCode 用户编码
     * @return 执行结果
     */
    List<RoleDetailEntity> getPrivateRoles(@Value("userCode") String userCode);

    /**
     * 根据用户编码获取所有角色信息
     *
     * @param userCode 用户编码
     * @return 执行结果
     */
    List<RoleDetailEntity> getAllRoles(@Value("userCode") String userCode);
}