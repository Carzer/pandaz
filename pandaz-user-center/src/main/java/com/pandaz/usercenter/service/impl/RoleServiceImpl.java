package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.custom.SecurityUser;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.RoleDetailEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.mapper.RoleMapper;
import com.pandaz.usercenter.mapper.RolePermissionMapper;
import com.pandaz.usercenter.service.RoleService;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
@CacheConfig(cacheManager = "secondaryCacheManager", cacheNames = {"user-center:role"})
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    /**
     * 角色mapper
     */
    private final RoleMapper roleMapper;

    /**
     * 角色-权限mapper
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtil<RoleEntity, RoleMapper> checkUtil;

    /**
     * 插入角色信息
     *
     * @param role 角色信息
     * @return int
     */
    @Override
    public int insert(RoleEntity role) {
        checkUtil.checkOrSetCode(role, roleMapper, "角色编码已存在", SysConstants.ROLE_PREFIX, null);
        role.setId(UuidUtil.getId());
        return roleMapper.insertSelective(role);
    }

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return 角色信息列表
     */
    @Cacheable(key = "#userCode+':'+((1 == #isPrivate)?'private':'public')")
    @Override
    public List<RoleDetailEntity> findByUserCode(String userCode, Byte isPrivate) {
        if (SysConstants.PRIVATE.equals(isPrivate)) {
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
     */
    @Cacheable(key = "#userCode+':all'")
    @Override
    public List<RoleDetailEntity> findByUserCode(String userCode) {
        return roleMapper.getAllRoles(userCode);
    }

    @Override
    public Set<GrantedAuthority> findBySecurityUser(SecurityUser securityUser) {
        List<RoleDetailEntity> roleDetailEntities = findByUserCode(securityUser.getUser().getCode());
        if (CollectionUtils.isEmpty(roleDetailEntities)) {
            return new HashSet<>();
        } else {
            return roleDetailEntities.stream().map(
                    role -> new SimpleGrantedAuthority(role.getCode())
            ).collect(Collectors.toSet());
        }
    }

    /**
     * 根据角色编码删除信息
     *
     * @param roleEntity 角色编码
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(RoleEntity roleEntity) {
        UpdateWrapper<RolePermissionEntity> rolePermissionEntityUpdateWrapper = new UpdateWrapper<>();
        rolePermissionEntityUpdateWrapper.eq("role_code", roleEntity.getCode());
        rolePermissionMapper.delete(rolePermissionEntityUpdateWrapper);
        UpdateWrapper<RoleEntity> roleEntityUpdateWrapper = new UpdateWrapper<>();
        roleEntityUpdateWrapper.eq("code", roleEntity.getCode());
        return roleMapper.delete(roleEntityUpdateWrapper);
    }

    /**
     * 根据编码查询
     *
     * @param code 角色编码
     * @return 角色信息
     */
    @Override
    public RoleEntity findByCode(String code) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", queryWrapper);
        return roleMapper.selectOne(queryWrapper);
    }

    /**
     * 分页查询
     *
     * @param roleEntity 角色信息
     * @return 分页结果
     */
    @Override
    public IPage<RoleEntity> getPage(RoleEntity roleEntity) {
        Page<RoleEntity> page = new Page<>(roleEntity.getPageNum(), roleEntity.getPageSize());
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(roleEntity.getCode())) {
            queryWrapper.likeRight("code", roleEntity.getCode());
        }
        if (StringUtils.hasText(roleEntity.getName())) {
            queryWrapper.likeRight("name", roleEntity.getName());
        }
        return page(page, queryWrapper);
    }

    /**
     * 更新方法
     *
     * @param roleEntity 角色信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(RoleEntity roleEntity) {
        UpdateWrapper<RoleEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", roleEntity.getCode());
        return roleMapper.delete(updateWrapper);
    }
}
