package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.entity.UserGroupEntity;
import com.pandaz.usercenter.mapper.UserGroupMapper;
import com.pandaz.usercenter.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
     * @return执行结果
     */
    @Override
    public List<UserGroupEntity> findByUserCode(UserGroupEntity userGroup) {
        QueryWrapper<UserGroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_code", userGroup.getUserCode());
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
     * @param userGroup userGroup
     */
    @Override
    public int insert(UserGroupEntity userGroup) {
        return userGroupMapper.insertSelective(userGroup);
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
        return 0;
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
        List<String> codesToRemove = existingCodes.stream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.stream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        userGroupEntity.setDeletedBy(operator);
        userGroupEntity.setDeletedDate(currentDate);
        deleteByCodes(userGroupEntity, codesToRemove);
        // 保存关系
        List<UserGroupEntity> list = codesToAdd.stream().map(code -> {
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
        List<String> codesToRemove = existingCodes.stream().filter(code -> !(newCodes.contains(code))).collect(Collectors.toList());
        List<String> codesToAdd = newCodes.stream().filter(code -> !(existingCodes.contains(code))).collect(Collectors.toList());
        // 清理之前的关系
        userGroupEntity.setDeletedBy(operator);
        userGroupEntity.setDeletedDate(currentDate);
        deleteByCodes(userGroupEntity, codesToRemove);
        // 保存关系
        List<UserGroupEntity> list = codesToAdd.stream().map(code -> {
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
     * 删除绑定权限
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
