package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.mapper.GroupRoleMapper;
import com.pandaz.usercenter.service.GroupRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @return java.util.List<com.pandaz.usercenter.entity.GroupRoleEntity>
     */
    @Override
    public List<GroupRoleEntity> findByGroupCode(GroupRoleEntity groupRole) {
        QueryWrapper<GroupRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_code", groupRole.getGroupCode());
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
        return groupRoleMapper.batchLogicDelete(map);
    }

}
