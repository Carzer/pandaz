package com.github.pandaz.auth.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.PermissionEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.auth.entity.RolePermissionEntity;
import com.github.pandaz.auth.mapper.RolePermissionMapper;
import com.github.pandaz.auth.service.RolePermissionService;
import com.github.pandaz.commons.util.ListUtil;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
     * 绑定权限
     *
     * @param operator             操作人
     * @param currentDate          当前时间
     * @param rolePermissionEntity 权限信息
     * @return 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheUpdate(name = "permissions", key = "#rolePermissionEntity.osCode+':'+#rolePermissionEntity.roleCode", value = "#rolePermissionEntity.permissionCodes")
    public int bindPermissions(String operator, LocalDateTime currentDate, RolePermissionEntity rolePermissionEntity) {
        String roleCode = rolePermissionEntity.getRoleCode();
        String osCode = rolePermissionEntity.getOsCode();
        String menuCode = rolePermissionEntity.getMenuCode();
        List<String> existingCodes = rolePermissionMapper.listBindCodes(rolePermissionEntity);
        List<String> newCodes = rolePermissionEntity.getPermissionCodes();
        // 已经存在的编码，如果没有在新list中存在，则准备删除
        List<String> codesToRemove = ListUtil.more(existingCodes, newCodes);
        // 新list中增加的编码，则准备保存
        List<String> codesToSave = ListUtil.more(newCodes, existingCodes);
        // 清理之前的角色菜单关系
        rolePermissionEntity.setDeletedBy(operator);
        rolePermissionEntity.setDeletedDate(currentDate);
        deleteByCodes(rolePermissionEntity, codesToRemove);

        // 保存关系
        List<RolePermissionEntity> list = codesToSave.stream().map(code -> {
            RolePermissionEntity rolePermission = new RolePermissionEntity();
            rolePermission.setId(UuidUtil.getId());
            rolePermission.setPermissionCode(code);
            rolePermission.setRoleCode(roleCode);
            rolePermission.setOsCode(osCode);
            rolePermission.setMenuCode(menuCode);
            rolePermission.setCreatedBy(operator);
            rolePermission.setCreatedDate(currentDate);
            return rolePermission;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return rolePermissionMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 查询已绑定的权限编码
     *
     * @param rolePermissionEntity 查询条件
     * @return 权限编码
     */
    @Override
    public List<String> listBindCodes(RolePermissionEntity rolePermissionEntity) {
        return rolePermissionMapper.listBindCodes(rolePermissionEntity);
    }

    /**
     * 根据系统编码和角色编码查询
     *
     * @param osCode   系统编码
     * @param roleCode 角色编码
     * @return 权限列表
     */
    @Override
    @Cached(name = "permissions:", key = "#osCode+':'+#roleCode", cacheType = CacheType.REMOTE, expire = 30, timeUnit = TimeUnit.MINUTES)
    public List<String> getByOsCodeAndRoleCode(String osCode, String roleCode) {
        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setOsCode(osCode);
        rolePermissionEntity.setRoleCode(roleCode);
        List<RolePermissionEntity> permissionList = rolePermissionMapper.listByOsCodeAndRoleCode(rolePermissionEntity);
        List<String> codeList = new ArrayList<>();
        permissionList.forEach(permission -> codeList.add("/" + permission.getPermissionCode()));
        return codeList;
    }

    /**
     * 删除绑定权限
     *
     * @param rolePermissionEntity rolePermissionEntity
     * @param codes                codes
     */
    private void deleteByCodes(RolePermissionEntity rolePermissionEntity, List<String> codes) {
        if (!CollectionUtils.isEmpty(codes)) {
            Map<String, Object> map = new HashMap<>(6);
            map.put("deletedBy", rolePermissionEntity.getDeletedBy());
            map.put("deletedDate", rolePermissionEntity.getDeletedDate());
            map.put("roleCode", rolePermissionEntity.getRoleCode());
            map.put("osCode", rolePermissionEntity.getOsCode());
            map.put("menuCode", rolePermissionEntity.getMenuCode());
            map.put("list", codes);
            rolePermissionMapper.batchLogicDelete(map);
        }
    }
}
