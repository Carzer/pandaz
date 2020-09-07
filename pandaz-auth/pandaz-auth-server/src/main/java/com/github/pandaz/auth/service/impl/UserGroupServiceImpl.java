package com.github.pandaz.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.GroupEntity;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserGroupEntity;
import com.github.pandaz.auth.mapper.UserGroupMapper;
import com.github.pandaz.auth.service.UserGroupService;
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
 * 用户-组服务
 *
 * @author Carzer
 * @since 2019-11-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroupEntity> implements UserGroupService {

    /**
     * 用户-组mapper
     */
    private final UserGroupMapper userGroupMapper;

    /**
     * 根据用户编码查询
     *
     * @param userGroup userGroup
     * @return 执行结果
     */
    @Override
    public List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup) {
        QueryWrapper<UserGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code", userGroup.getUserCode());
        if (SysConstants.PRIVATE.equals(userGroup.getIsPrivate())) {
            queryWrapper.eq("is_private", SysConstants.PRIVATE);
        }
        return userGroupMapper.selectList(queryWrapper);
    }

    /**
     * 根据用户编码删除
     *
     * @param userEntity userEntity
     * @return int
     */
    @Override
    public int deleteByUserCode(UserEntity userEntity) {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setUserCode(userEntity.getCode());
        userGroupEntity.setDeletedBy(userEntity.getDeletedBy());
        userGroupEntity.setDeletedDate(userEntity.getDeletedDate());
        return userGroupMapper.logicDeleteByUserCode(userGroupEntity);
    }

    /**
     * 根据用户组编码删除
     *
     * @param groupEntity groupEntity
     * @return int
     */
    @Override
    public int deleteByGroupCode(GroupEntity groupEntity) {
        UserGroupEntity userGroupEntity = new UserGroupEntity();
        userGroupEntity.setGroupCode(groupEntity.getCode());
        userGroupEntity.setDeletedBy(groupEntity.getDeletedBy());
        userGroupEntity.setDeletedDate(groupEntity.getDeletedDate());
        return userGroupMapper.logicDeleteByGroupCode(userGroupEntity);
    }

    /**
     * 插入方法
     *
     * @param userGroupEntity userGroup
     */
    @Override
    public int insert(UserGroupEntity userGroupEntity) {
        if (!StringUtils.hasText(userGroupEntity.getId())) {
            userGroupEntity.setId(UuidUtil.getId());
        }
        return userGroupMapper.insertSelective(userGroupEntity);
    }

    /**
     * 绑定用户组成员
     *
     * @param operator        操作者
     * @param currentDate     操作时间
     * @param userGroupEntity 用户信息
     * @return 执行结果
     */
    @Override
    public int bindGroupMember(String operator, LocalDateTime currentDate, UserGroupEntity userGroupEntity) {
        String groupCode = userGroupEntity.getGroupCode();
        userGroupEntity.setUserCode(null);
        List<String> existingCodes = userGroupMapper.listBindGroupMembers(userGroupEntity);
        List<String> newCodes = userGroupEntity.getUserCodes();
        List<String> codesToRemove = existingCodes.parallelStream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.parallelStream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        userGroupEntity.setDeletedBy(operator);
        userGroupEntity.setDeletedDate(currentDate);
        deleteByCodes(userGroupEntity, codesToRemove);
        // 保存关系
        List<UserGroupEntity> list = codesToAdd.parallelStream().map(code -> {
            UserGroupEntity userGroup = new UserGroupEntity();
            userGroup.setId(UuidUtil.getId());
            userGroup.setGroupCode(groupCode);
            userGroup.setUserCode(code);
            userGroup.setCreatedBy(operator);
            userGroup.setCreatedDate(currentDate);
            return userGroup;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return userGroupMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 绑定用户与用户组关系
     *
     * @param operator        操作者
     * @param currentDate     操作时间
     * @param userGroupEntity 用户信息
     * @return 执行结果
     */
    @Override
    public int bindUserGroup(String operator, LocalDateTime currentDate, UserGroupEntity userGroupEntity) {
        String userCode = userGroupEntity.getUserCode();
        userGroupEntity.setGroupCode(null);
        List<String> existingCodes = userGroupMapper.listBindUserGroups(userGroupEntity);
        List<String> newCodes = userGroupEntity.getGroupCodes();
        List<String> codesToRemove = existingCodes.parallelStream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.parallelStream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        userGroupEntity.setDeletedBy(operator);
        userGroupEntity.setDeletedDate(currentDate);
        deleteByCodes(userGroupEntity, codesToRemove);
        // 保存关系
        List<UserGroupEntity> list = codesToAdd.parallelStream().map(code -> {
            UserGroupEntity userGroup = new UserGroupEntity();
            userGroup.setId(UuidUtil.getId());
            userGroup.setGroupCode(code);
            userGroup.setUserCode(userCode);
            userGroup.setCreatedBy(operator);
            userGroup.setCreatedDate(currentDate);
            return userGroup;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return userGroupMapper.batchInsert(list);
        }
        return 0;
    }

    /**
     * 列出组内成员
     *
     * @param userGroupEntity 查询条件
     * @return 执行结果
     */
    @Override
    public List<String> listBindGroupMembers(UserGroupEntity userGroupEntity) {
        return userGroupMapper.listBindGroupMembers(userGroupEntity);
    }

    /**
     * 列出用户所有的组
     *
     * @param userGroupEntity 查询条件
     * @return 执行结果
     */
    @Override
    public List<String> listBindUserGroups(UserGroupEntity userGroupEntity) {
        return userGroupMapper.listBindUserGroups(userGroupEntity);
    }

    /**
     * 删除绑定关系
     *
     * @param userGroupEntity userGroupEntity
     * @param codes           codes
     */
    private void deleteByCodes(UserGroupEntity userGroupEntity, List<String> codes) {
        if (!CollectionUtils.isEmpty(codes)) {
            Map<String, Object> map = new HashMap<>(5);
            map.put("deletedBy", userGroupEntity.getDeletedBy());
            map.put("deletedDate", userGroupEntity.getDeletedDate());
            map.put("userCode", userGroupEntity.getUserCode());
            map.put("groupCode", userGroupEntity.getGroupCode());
            map.put("list", codes);
            userGroupMapper.batchLogicDelete(map);
        }
    }
}
