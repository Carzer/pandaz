package com.github.pandaz.auth.mapper;

import com.github.pandaz.commons.mapper.BasisMapper;
import com.github.pandaz.auth.entity.RoleDetailEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 角色mapper
 *
 * @author Carzer
 * @since 2019-10-23
 */
public interface RoleMapper extends BasisMapper<RoleEntity> {

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