package com.pandaz.usercenter.service.impl;

import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.RoleEntity;
import com.pandaz.usercenter.mapper.GroupMapper;
import com.pandaz.usercenter.service.GroupRoleService;
import com.pandaz.usercenter.service.GroupService;
import com.pandaz.usercenter.service.RoleService;
import com.pandaz.usercenter.util.CheckUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * pandaz:com.pandaz.usercenter.service.impl
 * <p>
 * 组服务
 *
 * @author Carzer
 * Date: 2019-10-25 11:01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl implements GroupService {

    /**
     * 角色mapper
     */
    private final RoleService roleService;

    /**
     * 组-角色服务
     */
    private final GroupRoleService groupRoleService;

    /**
     * 编码检查工具
     */
    private final CheckUtils<GroupEntity, GroupMapper> checkUtils;

    /**
     * 组mapper
     */
    private final GroupMapper groupMapper;

    /**
     * 插入组信息
     *
     * @param group group
     * @return int
     * @author Carzer
     * Date: 2019/10/25 11:02
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(GroupEntity group) {
        //组信息补充
        String groupCode = checkUtils.checkOrSetCode(group, groupMapper, "组编码已存在", SysConstants.GROUP_PREFIX, null);
        group.setId(UuidUtil.getUnsignedUuid());
        String groupName = group.getName();
        String createdBy = group.getCreatedBy();
        Date createDate = group.getCreatedDate();

        //创建私有角色
        RoleEntity role = new RoleEntity();
        String roleCode = SysConstants.ROLE_PREFIX + groupCode;
        role.setCode(roleCode);
        role.setIsPrivate(SysConstants.IS_PRIVATE);
        role.setName(groupName + SysConstants.PRIVATE_ROLE);
        role.setCreatedBy(createdBy);
        role.setCreatedDate(createDate);
        //关联组及私有角色
        GroupRoleEntity groupRole = new GroupRoleEntity();
        groupRole.setId(UuidUtil.getUnsignedUuid());
        groupRole.setIsPrivate(SysConstants.IS_PRIVATE);
        groupRole.setGroupCode(groupCode);
        groupRole.setRoleCode(roleCode);
        groupRole.setCreatedBy(createdBy);
        groupRole.setCreatedDate(createDate);
        groupRoleService.insert(groupRole);
        roleService.insert(role);
        return groupMapper.insertSelective(group);
    }

    /**
     * 根据编码删除
     *
     * @param groupCode groupCode
     * @return int
     * @author Carzer
     * Date: 2019/10/25 15:57
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByCode(String groupCode) {
        GroupRoleEntity groupRole = new GroupRoleEntity();
        groupRole.setGroupCode(groupCode);
        groupRole.setIsPrivate(SysConstants.IS_PRIVATE);
        //查询私有角色，并清理
        List<GroupRoleEntity> roleList = groupRoleService.findByGroupCode(groupRole);
        if (!CollectionUtils.isEmpty(roleList)) {
            for (GroupRoleEntity role : roleList) {
                String roleCode = role.getRoleCode();
                roleService.deleteByCode(roleCode);
            }
        }
        //清理所有关系
        groupRoleService.deleteByGroupCode(groupCode);
        //最终删除组信息
        return groupMapper.deleteByCode(groupCode);
    }
}
