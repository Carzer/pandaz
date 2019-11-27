package com.pandaz.usercenter.service.impl;

import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.mapper.RoleMapper;
import com.pandaz.usercenter.service.RoleService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 角色服务
 *
 * @author Carzer
 * Date: 2019-10-25 11:21
 */
@CacheConfig(cacheManager = "secondaryCacheManager", cacheNames = {"user-center:role"})
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl implements RoleService {

    /**
     * 角色mapper
     */
    private final RoleMapper roleMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtils<RoleEntity, RoleMapper> checkUtils;

    /**
     * 插入角色信息
     *
     * @param role 角色信息
     * @return int
     * @author Carzer
     * Date: 2019/10/25 11:30
     */
    @Override
    public int insert(RoleEntity role) {
        checkUtils.checkOrSetCode(role, roleMapper, "角色编码已存在", SysConstants.ROLE_PREFIX, null);
        role.setId(UuidUtil.getUnsignedUuid());
        return roleMapper.insertSelective(role);
    }

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * Date: 2019/10/25 11:22
     */
    @Cacheable(key = "#userCode+':'+((1 == #isPrivate)?'private':'public')")
    @Override
    public List<RoleDetailEntity> findByUserCode(String userCode, Byte isPrivate) {
        if (SysConstants.IS_PRIVATE.equals(isPrivate)) {
            return roleMapper.getPrivateRoles(userCode);
        } else {
            return roleMapper.getPublicRoles(userCode);
        }
    }

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode userCode用户编码
     * @return java.util.List<com.pandaz.usercenter.entity.RoleEntity>
     * @author Carzer
     * Date: 2019/10/25 11:22
     */
    @Cacheable(key = "#userCode+':all'")
    @Override
    public List<RoleDetailEntity> findByUserCode(String userCode) {
        return roleMapper.getAllRoles(userCode);
    }

    /**
     * 根据角色编码删除信息
     *
     * @param roleCode roleCode
     * @return int
     * @author Carzer
     * Date: 2019/10/25 16:20
     */
    @Override
    public int deleteByCode(String roleCode) {
        return roleMapper.deleteByCode(roleCode);
    }
}
