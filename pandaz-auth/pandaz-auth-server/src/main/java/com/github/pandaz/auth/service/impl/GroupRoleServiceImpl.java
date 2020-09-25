package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.entity.GroupEntity;
import com.github.pandaz.auth.entity.GroupRoleEntity;
import com.github.pandaz.auth.entity.RoleEntity;
import com.github.pandaz.auth.mapper.GroupRoleMapper;
import com.github.pandaz.auth.service.GroupRoleService;
import com.github.pandaz.commons.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组-角色服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupRoleServiceImpl extends ServiceImpl<GroupRoleMapper, GroupRoleEntity> implements GroupRoleService {

    /**
     * 组-角色mapper
     */
    private final GroupRoleMapper groupRoleMapper;

    /**
     * 插入方法
     *
     * @param groupRoleEntity groupRoleEntity
     * @return int
     */
    @Override
    public int insert(GroupRoleEntity groupRoleEntity) {
        if (!StringUtils.hasText(groupRoleEntity.getId())) {
            groupRoleEntity.setId(UuidUtil.getId());
        }
        return groupRoleMapper.insertSelective(groupRoleEntity);
    }

    /**
     * 根据组编码查询
     *
     * @param groupRole groupRole
     * @return 执行结果
     */
    @Override
    public List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole) {
        QueryWrapper<GroupRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupRoleEntity::getGroupCode, groupRole.getGroupCode());
        return groupRoleMapper.selectList(queryWrapper);
    }

    /**
     * 跟组编码删除
     *
     * @param groupEntity groupCode
     * @return int
     */
    @Override
    public int deleteByGroupCode(GroupEntity groupEntity) {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setGroupCode(groupEntity.getCode());
        groupRoleEntity.setDeletedBy(groupEntity.getDeletedBy());
        groupRoleEntity.setDeletedDate(groupEntity.getDeletedDate());
        return groupRoleMapper.logicDeleteByGroupCode(groupRoleEntity);
    }

    /**
     * 跟角色编码删除
     *
     * @param roleEntity 删除信息
     * @return int
     */
    @Override
    public int deleteByRoleCode(RoleEntity roleEntity) {
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setRoleCode(roleEntity.getCode());
        groupRoleEntity.setDeletedBy(roleEntity.getDeletedBy());
        groupRoleEntity.setDeletedDate(roleEntity.getDeletedDate());
        return groupRoleMapper.logicDeleteByRoleCode(groupRoleEntity);
    }

    /**
     * 列出绑定的角色
     *
     * @param groupRoleEntity 条件
     * @return 角色
     */
    @Override
    public List<String> listBindGroupRoles(GroupRoleEntity groupRoleEntity) {
        return groupRoleMapper.listBindGroupRoles(groupRoleEntity);
    }

    /**
     * 列出绑定的组
     *
     * @param groupRoleEntity 条件
     * @return 组
     */
    @Override
    public List<String> listBindRoleGroups(GroupRoleEntity groupRoleEntity) {
        return groupRoleMapper.listBindRoleGroups(groupRoleEntity);
    }

    /**
     * 绑定组-角色关系
     *
     * @param operator        操作者
     * @param currentDate     当前时间
     * @param groupRoleEntity 组-角色关系
     * @return 执行结果
     */
    @Override
    public int bindGroupRole(String operator, LocalDateTime currentDate, GroupRoleEntity groupRoleEntity) {
        String groupCode = groupRoleEntity.getGroupCode();
        groupRoleEntity.setRoleCode(null);
        List<String> existingCodes = groupRoleMapper.listBindGroupRoles(groupRoleEntity);
        List<String> newCodes = groupRoleEntity.getRoleCodes();
        List<String> codesToRemove = existingCodes.stream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.stream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        groupRoleEntity.setDeletedBy(operator);
        groupRoleEntity.setDeletedDate(currentDate);
        deleteByCodes(groupRoleEntity, codesToRemove);
        // 保存关系
        List<GroupRoleEntity> list = codesToAdd.stream().map(code -> {
            GroupRoleEntity groupRole = new GroupRoleEntity();
            groupRole.setId(UuidUtil.getId());
            groupRole.setGroupCode(groupCode);
            groupRole.setRoleCode(code);
            groupRole.setCreatedBy(operator);
            groupRole.setCreatedDate(currentDate);
            return groupRole;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return groupRoleMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 绑定角色-组关系
     *
     * @param operator        操作者
     * @param currentDate     当前时间
     * @param groupRoleEntity 组-角色关系
     * @return 执行结果
     */
    @Override
    public int bindRoleGroup(String operator, LocalDateTime currentDate, GroupRoleEntity groupRoleEntity) {
        String roleCode = groupRoleEntity.getRoleCode();
        groupRoleEntity.setGroupCode(null);
        List<String> existingCodes = groupRoleMapper.listBindRoleGroups(groupRoleEntity);
        List<String> newCodes = groupRoleEntity.getGroupCodes();
        List<String> codesToRemove = existingCodes.stream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.stream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        groupRoleEntity.setDeletedBy(operator);
        groupRoleEntity.setDeletedDate(currentDate);
        deleteByCodes(groupRoleEntity, codesToRemove);
        // 保存关系
        List<GroupRoleEntity> list = codesToAdd.stream().map(code -> {
            GroupRoleEntity groupRole = new GroupRoleEntity();
            groupRole.setId(UuidUtil.getId());
            groupRole.setGroupCode(code);
            groupRole.setRoleCode(roleCode);
            groupRole.setCreatedBy(operator);
            groupRole.setCreatedDate(currentDate);
            return groupRole;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return groupRoleMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 删除绑定关系
     *
     * @param groupRoleEntity groupRoleEntity
     * @param codes           codes
     */
    private void deleteByCodes(GroupRoleEntity groupRoleEntity, List<String> codes) {
        if (!CollectionUtils.isEmpty(codes)) {
            Map<String, Object> map = new HashMap<>(5);
            map.put("deletedBy", groupRoleEntity.getDeletedBy());
            map.put("deletedDate", groupRoleEntity.getDeletedDate());
            map.put("roleCode", groupRoleEntity.getRoleCode());
            map.put("groupCode", groupRoleEntity.getGroupCode());
            map.put("list", codes);
            groupRoleMapper.batchLogicDelete(map);
        }
    }
}
