package com.github.pandaz.auth.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.dto.SecurityUser;
import com.github.pandaz.auth.entity.RoleDetailEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.auth.mapper.RoleMapper;
import com.github.pandaz.auth.service.GroupRoleService;
import com.github.pandaz.auth.service.RolePermissionService;
import com.github.pandaz.auth.service.RoleService;
import com.github.pandaz.auth.util.CheckUtil;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 角色服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    /**
     * 角色mapper
     */
    private final RoleMapper roleMapper;

    /**
     * 角色-权限服务
     */
    private final RolePermissionService rolePermissionService;

    /**
     * 组-角色服务
     */
    private final GroupRoleService groupRoleService;

    /**
     * 插入角色信息
     *
     * @param roleEntity 角色信息
     * @return int
     */
    @Override
    public int insert(RoleEntity roleEntity) {
        if (StringUtils.hasText(roleEntity.getCode())) {
            roleEntity.setCode(String.format("%s%s", SysConstants.ROLE_PREFIX, roleEntity.getCode()));
        }
        CheckUtil.checkOrSetCode(roleMapper, roleEntity, "角色编码重复", SysConstants.ROLE_PREFIX, null);
        roleEntity.setId(UuidUtil.getId());
        return roleMapper.insertSelective(roleEntity);
    }

    /**
     * 根据用户编码查询角色信息
     *
     * @param userCode  userCode用户编码
     * @param isPrivate 是否私有
     * @return 角色信息列表
     */
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
     * @return 执行结果
     */
    @Override
    public List<RoleDetailEntity> findByUserCode(String userCode) {
        return roleMapper.getAllRoles(userCode);
    }

    /**
     * 根据用户角色
     *
     * @param securityUser 安全用户类
     * @return 执行结果
     */
    @Override
    public Set<GrantedAuthority> findBySecurityUser(SecurityUser securityUser) {
        List<RoleDetailEntity> roleDetailEntities = findByUserCode(securityUser.getUser().getCode());
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleDetailEntities)) {
            // 转换为map，简单去重
            Map<String, String> authorityMap = roleDetailEntities.stream()
                    .collect(Collectors.toMap(RoleDetailEntity::getCode, RoleDetailEntity::getCode,
                            // 如果key值重复，可能会报重复错误
                            // 增加判断方法，如果重复，则保留第一个
                            (k1, k2) -> k1));
            authorityMap.forEach((key, value) -> authoritySet.add(new SimpleGrantedAuthority(value)));
        }
        return authoritySet;
    }

    /**
     * 获取所有角色编码
     *
     * @return 所有角色编码
     */
    @Override
    @Cached(name = "role:", key = "'allRoleCode'", cacheType = CacheType.LOCAL, expire = 30, timeUnit = TimeUnit.MINUTES)
    public List<String> listAllRoleCode() {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleEntity::getIsPrivate, 0);
        queryWrapper.lambda().eq(RoleEntity::getLocked, 0);
        return roleMapper.selectList(queryWrapper).stream().map(RoleEntity::getCode).collect(Collectors.toList());
    }

    /**
     * 根据角色编码删除信息
     *
     * @param roleEntity 角色编码
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int logicDeleteByCode(RoleEntity roleEntity) {
        rolePermissionService.deleteByRoleCode(roleEntity);
        groupRoleService.deleteByRoleCode(roleEntity);
        return roleMapper.logicDeleteByCode(roleEntity);
    }

    /**
     * 根据编码查询
     *
     * @param roleEntity 角色编码
     * @return 角色信息
     */
    @Override
    public RoleEntity findByCode(RoleEntity roleEntity) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleEntity::getCode, queryWrapper);
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
        queryWrapper.lambda().likeRight(StringUtils.hasText(roleEntity.getCode()), RoleEntity::getCode, roleEntity.getCode());
        queryWrapper.lambda().likeRight(StringUtils.hasText(roleEntity.getName()), RoleEntity::getName, roleEntity.getName());
        queryWrapper.lambda().eq(roleEntity.getLocked() != null, RoleEntity::getLocked, roleEntity.getLocked());
        queryWrapper.lambda().ge(roleEntity.getStartDate() != null, RoleEntity::getCreatedDate, roleEntity.getStartDate());
        queryWrapper.lambda().le(roleEntity.getEndDate() != null, RoleEntity::getCreatedDate, roleEntity.getEndDate());
        queryWrapper.lambda().eq(RoleEntity::getIsPrivate, SysConstants.PUBLIC);
        queryWrapper.lambda().orderByDesc(RoleEntity::getCreatedDate);
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
        updateWrapper.lambda().eq(RoleEntity::getCode, roleEntity.getCode());
        return roleMapper.update(roleEntity, updateWrapper);
    }

    /**
     * 批量删除用户
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int logicDeleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setCode(code);
            roleEntity.setDeletedBy(deletedBy);
            roleEntity.setDeletedDate(deletedDate);
            logicDeleteByCode(roleEntity);
        });
        return codes.size();
    }
}
