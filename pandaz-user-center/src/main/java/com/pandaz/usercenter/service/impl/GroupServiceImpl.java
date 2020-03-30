package com.pandaz.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.mapper.GroupMapper;
import com.pandaz.usercenter.service.GroupRoleService;
import com.pandaz.usercenter.service.GroupService;
import com.pandaz.usercenter.service.RoleService;
import com.pandaz.usercenter.service.UserGroupService;
import com.pandaz.usercenter.util.CheckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 组服务
 *
 * @author Carzer
 * @since 2019-10-25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupEntity> implements GroupService {

    /**
     * 角色mapper
     */
    private final RoleService roleService;

    /**
     * 组-角色服务
     */
    private final GroupRoleService groupRoleService;

    /**
     * 用户-组服务
     */
    private final UserGroupService userGroupService;

    /**
     * 编码检查工具
     */
    private final CheckUtil<GroupEntity, GroupMapper> checkUtil;

    /**
     * 组mapper
     */
    private final GroupMapper groupMapper;

    /**
     * 插入组信息
     *
     * @param groupEntity group
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(GroupEntity groupEntity) {
        // 组信息补充
        String groupCode = checkUtil.checkOrSetCode(groupEntity, groupMapper, "组编码已存在", SysConstants.GROUP_PREFIX, null);
        if (!StringUtils.hasText(groupEntity.getId())) {
            groupEntity.setId(UuidUtil.getId());
        }
        String groupName = groupEntity.getName();
        String createdBy = groupEntity.getCreatedBy();
        LocalDateTime createDate = groupEntity.getCreatedDate();

        // 创建私有角色
        RoleEntity role = new RoleEntity();
        role.setCode(groupCode);
        role.setIsPrivate(SysConstants.PRIVATE);
        role.setName(String.format("%s%s", groupName, SysConstants.PRIVATE_ROLE));
        role.setCreatedBy(createdBy);
        role.setCreatedDate(createDate);
        // 关联组及私有角色
        GroupRoleEntity groupRoleEntity = new GroupRoleEntity();
        groupRoleEntity.setId(UuidUtil.getId());
        groupRoleEntity.setIsPrivate(SysConstants.PRIVATE);
        groupRoleEntity.setGroupCode(groupCode);
        groupRoleEntity.setRoleCode(groupCode);
        groupRoleEntity.setCreatedBy(createdBy);
        groupRoleEntity.setCreatedDate(createDate);
        groupRoleService.insert(groupRoleEntity);
        roleService.insert(role);
        return groupMapper.insertSelective(groupEntity);
    }

    /**
     * 根据编码删除
     *
     * @param groupEntity 组信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(GroupEntity groupEntity) {
        GroupRoleEntity groupRole = new GroupRoleEntity();
        groupRole.setGroupCode(groupEntity.getCode());
        groupRole.setIsPrivate(SysConstants.PRIVATE);
        // 查询私有角色，并清理
        List<GroupRoleEntity> roleList = groupRoleService.findByGroupCode(groupRole);
        if (!CollectionUtils.isEmpty(roleList)) {
            String deletedBy = groupEntity.getDeletedBy();
            LocalDateTime deletedDate = groupEntity.getDeletedDate();
            roleList.forEach(entity -> {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setCode(entity.getRoleCode());
                roleEntity.setDeletedBy(deletedBy);
                roleEntity.setDeletedDate(deletedDate);
                roleService.deleteByCode(roleEntity);
            });
        }
        // 清理所有关系
        groupRoleService.deleteByGroupCode(groupEntity);
        userGroupService.deleteByGroupCode(groupEntity);
        // 最终删除组信息
        return groupMapper.logicDelete(groupEntity);
    }

    /**
     * 根据编码删除
     *
     * @param code 组编码
     */
    @Override
    public GroupEntity findByCode(String code) {
        QueryWrapper<GroupEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return groupMapper.selectOne(queryWrapper);
    }

    /**
     * 分页方法
     *
     * @param groupEntity 查询信息
     * @return 查询结果
     */
    @Override
    public IPage<GroupEntity> getPage(GroupEntity groupEntity) {
        Page<GroupEntity> page = new Page<>(groupEntity.getPageNum(), groupEntity.getPageSize());
        QueryWrapper<GroupEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(groupEntity.getName())) {
            queryWrapper.likeRight("name", groupEntity.getName());
        }
        if (StringUtils.hasText(groupEntity.getCode())) {
            queryWrapper.likeRight("code", groupEntity.getCode());
        }
        queryWrapper.eq("is_private", SysConstants.PUBLIC);
        queryWrapper.orderByDesc(SysConstants.CREATED_DATE_COLUMN);
        return page(page, queryWrapper);
    }

    /**
     * 更新方法
     *
     * @param groupEntity 组信息
     * @return 执行结果
     */
    @Override
    public int updateByCode(GroupEntity groupEntity) {
        UpdateWrapper<GroupEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", groupEntity.getCode());
        return groupMapper.update(groupEntity, updateWrapper);
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCodes(String deletedBy, LocalDateTime deletedDate, List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return 0;
        }
        codes.forEach(code -> {
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setCode(code);
            groupEntity.setDeletedBy(deletedBy);
            groupEntity.setDeletedDate(deletedDate);
            deleteByCode(groupEntity);
        });
        return codes.size();
    }

}
