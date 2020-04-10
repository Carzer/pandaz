package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.PermissionEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.entity.RolePermissionEntity;
import com.pandaz.usercenter.mapper.RolePermissionMapper;
import com.pandaz.usercenter.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色-权限服务
 *
 * @author Carzer
 * @since 2019-11-06
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {

    /**
     * 角色-权限mapper
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * 插入方法
     *
     * @param rolePermissionEntity rolePermission
     * @return int
     */
    @Override
    public int insert(RolePermissionEntity rolePermissionEntity) {
        return rolePermissionMapper.insertSelective(rolePermissionEntity);
    }

    /**
     * 根据角色编码删除
     *
     * @param roleEntity 关系
     * @return 执行结果
     */
    @Override
    public int deleteByRoleCode(RoleEntity roleEntity) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setRoleCode(roleEntity.getCode());
        rolePermissionEntity.setDeletedBy(roleEntity.getDeletedBy());
        rolePermissionEntity.setDeletedDate(roleEntity.getDeletedDate());
        return rolePermissionMapper.logicDeleteByRoleCode(rolePermissionEntity);
    }

    /**
     * 根据权限编码删除
     *
     * @param permissionEntity 关系
     * @return 执行结果
     */
    @Override
    public int deleteByPermissionCode(PermissionEntity permissionEntity) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermissionCode(permissionEntity.getCode());
        rolePermissionEntity.setDeletedBy(permissionEntity.getDeletedBy());
        rolePermissionEntity.setDeletedDate(permissionEntity.getDeletedDate());
        return rolePermissionMapper.logicDeleteByPermissionCode(rolePermissionEntity);
    }

    /**
     * 批量删除
     *
     * @param deletedBy   删除人
     * @param deletedDate 删除时间
     * @param codes       编码
     * @return 执行结果
     */
    @Override
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("deletedBy", deletedBy);
        map.put("deletedDate", deletedDate);
        map.put("list", codes);
        return rolePermissionMapper.batchLogicDelete(map);
    }

    /**
     * 绑定权限
     *
     * @param operator           操作人
     * @param currentDate        当前时间
     * @param roleCode           角色编码
     * @param permissionEntities 权限信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bindPermission(String operator, LocalDateTime currentDate, String roleCode, List<PermissionEntity> permissionEntities) {
        // 清理之前的角色菜单关系
        // todo 根据前端决定全部清理还是清理部分菜单
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setCode(roleCode);
        roleEntity.setDeletedDate(currentDate);
        roleEntity.setDeletedBy(operator);
        deleteByRoleCode(roleEntity);

        // 保存关系
        List<RolePermissionEntity> list = permissionEntities.stream().map(permissionEntity -> {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setId(UuidUtil.getId());
            rolePermissionEntity.setPermissionCode(permissionEntity.getCode());
            rolePermissionEntity.setRoleCode(roleCode);
            rolePermissionEntity.setOsCode(permissionEntity.getOsCode());
            rolePermissionEntity.setMenuCode(permissionEntity.getMenuCode());
            rolePermissionEntity.setCreatedBy(operator);
            rolePermissionEntity.setCreatedDate(currentDate);
            return rolePermissionEntity;
        }).collect(Collectors.toList());
        return rolePermissionMapper.batchInsert(list);
    }

}
