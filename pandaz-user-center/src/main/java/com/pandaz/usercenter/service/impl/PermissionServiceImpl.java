package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.mapper.PermissionMapper;
import com.pandaz.usercenter.mapper.RolePermissionMapper;
import com.pandaz.usercenter.service.PermissionService;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 权限相关服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    /**
     * 权限mapper
     */
    private final PermissionMapper permissionMapper;

    /**
     * 角色权限mapper
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 编码检查工具
     */
    private final CheckUtil<PermissionEntity, PermissionMapper> checkUtil;

    /**
     * 插入方法
     *
     * @param permission permission
     * @return com.pandaz.usercenter.entity.PermissionEntity
     */
    @Override
    public PermissionEntity insert(PermissionEntity permission) {
        checkUtil.checkOrSetCode(permission, permissionMapper, "权限编码已存在");
        if (!StringUtils.hasText(permission.getId())) {
            permission.setId(UuidUtil.getId());
        }
        permissionMapper.insertSelective(permission);
        return permission;
    }

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 查询结果
     */
    @Override
    public PermissionEntity findByCode(String code) {
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return permissionMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param permissionEntity 权限信息
     * @return 分页结果
     */
    @Override
    public IPage<PermissionEntity> getPage(PermissionEntity permissionEntity) {
        Page<PermissionEntity> page = new Page<>(permissionEntity.getPageNum(), permissionEntity.getPageSize());
        QueryWrapper<PermissionEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(permissionEntity.getCode())) {
            queryWrapper.likeRight("code", permissionEntity.getCode());
        }
        if (StringUtils.hasText(permissionEntity.getName())) {
            queryWrapper.likeRight("name", permissionEntity.getName());
        }
        return page(page, queryWrapper);
    }

    /**
     * 更新方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(PermissionEntity permissionEntity) {
        UpdateWrapper<PermissionEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", permissionEntity.getCode());
        return permissionMapper.update(permissionEntity, updateWrapper);
    }

    /**
     * 删除方法
     *
     * @param permissionEntity 权限信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(PermissionEntity permissionEntity) {
        UpdateWrapper<PermissionEntity> permissionEntityUpdateWrapper = new UpdateWrapper<>();
        permissionEntityUpdateWrapper.eq("code", permissionEntity.getCode());
        UpdateWrapper<RolePermissionEntity> rolePermissionEntityUpdateWrapper = new UpdateWrapper<>();
        rolePermissionEntityUpdateWrapper.eq("permission_code", permissionEntity.getCode());
        rolePermissionMapper.delete(rolePermissionEntityUpdateWrapper);
        return permissionMapper.delete(permissionEntityUpdateWrapper);
    }
}
