package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.*;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.entity.GroupRoleEntity;
import com.pandaz.usercenter.entity.UserGroupEntity;
import com.pandaz.usercenter.service.GroupRoleService;
import com.pandaz.usercenter.service.GroupService;
import com.pandaz.usercenter.service.UserGroupService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 组信息
 *
 * @author Carzer
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/group")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    /**
     * 组服务
     */
    private final GroupService groupService;

    /**
     * 用户-组服务
     */
    private final UserGroupService userGroupService;

    /**
     * 组-角色服务
     */
    private final GroupRoleService groupRoleService;

    /**
     * 工具类
     */
    private final ControllerUtil<GroupService> controllerUtil;

    /**
     * 查询方法
     *
     * @param groupDTO 查询条件
     * @return 组信息
     */
    @GetMapping(UrlConstants.GET)
    public R<GroupDTO> get(@Valid GroupDTO groupDTO) {
        GroupDTO result = BeanCopyUtil.copy(groupService.findByCode(groupDTO.getCode()), GroupDTO.class);
        return new R<>(result);
    }

    /**
     * 分页方法
     *
     * @param groupDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public R<Map<String, Object>> getPage(GroupDTO groupDTO) {
        IPage<GroupEntity> page = groupService.getPage(BeanCopyUtil.copy(groupDTO, GroupEntity.class));
        return new R<>(BeanCopyUtil.convertToMap(page, GroupDTO.class));
    }

    /**
     * 新增方法
     *
     * @param groupDTO 组信息
     * @return 组信息
     */
    @PostMapping(UrlConstants.INSERT)
    public R<GroupDTO> insert(@RequestBody GroupDTO groupDTO, Principal principal) {
        check(groupDTO);
        GroupEntity groupEntity = BeanCopyUtil.copy(groupDTO, GroupEntity.class);
        groupEntity.setId(UuidUtil.getId());
        if (StringUtils.hasText(groupEntity.getCode())) {
            groupEntity.setCode(String.format("%s%s", SysConstants.GROUP_PREFIX, groupEntity.getCode()));
        }
        groupEntity.setCreatedBy(principal.getName());
        groupEntity.setCreatedDate(LocalDateTime.now());
        groupService.insert(groupEntity);
        return R.success();
    }

    /**
     * 更新方法
     *
     * @param groupDTO 组信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public R<String> update(@Valid @RequestBody GroupDTO groupDTO, Principal principal) {
        check(groupDTO);
        GroupEntity groupEntity = BeanCopyUtil.copy(groupDTO, GroupEntity.class);
        groupEntity.setUpdatedBy(principal.getName());
        groupEntity.setUpdatedDate(LocalDateTime.now());
        groupService.updateByCode(groupEntity);
        return R.success();
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public R<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(groupService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @GetMapping("/getUserPage")
    public R<Map<String, Object>> getUserPage(UserDTO userDTO) {
        return new R<>(controllerUtil.getUserPage(userDTO));
    }

    /**
     * 绑定用户与组关系
     *
     * @param userGroupDTO 用户-组信息
     * @return 执行结果
     */
    @PutMapping("/bindGroupMember")
    public R<String> bindGroupMember(@Valid @RequestBody UserGroupDTO userGroupDTO, Principal principal) {
        userGroupService.bindGroupMember(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(userGroupDTO, UserGroupEntity.class));
        return R.success();
    }

    /**
     * 列出组内成员
     *
     * @param userGroupDTO 查询条件
     * @return 执行结果
     */
    @GetMapping("/listBindGroupMembers")
    public R<List<String>> listBindGroupMembers(UserGroupDTO userGroupDTO) {
        List<String> list = userGroupService.listBindGroupMembers(BeanCopyUtil.copy(userGroupDTO, UserGroupEntity.class));
        return new R<>(list);
    }

    /**
     * 分页方法
     *
     * @param roleDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getRolePage")
    public R<Map<String, Object>> getRolePage(RoleDTO roleDTO) {
        return new R<>(controllerUtil.getRolePage(roleDTO));
    }

    /**
     * 绑定用户与组关系
     *
     * @param groupRoleDTO 组-角色信息
     * @return 执行结果
     */
    @PutMapping("/bindRole")
    public R<String> bindRole(@RequestBody GroupRoleDTO groupRoleDTO, Principal principal) {
        groupRoleService.bindGroupRole(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(groupRoleDTO, GroupRoleEntity.class));
        return R.success();
    }

    /**
     * 列出绑定的角色
     *
     * @param groupRoleDTO 查询条件
     * @return 执行结果
     */
    @GetMapping("/listBindRoles")
    public R<List<String>> listBindRoles(GroupRoleDTO groupRoleDTO) {
        List<String> list = groupRoleService.listBindGroupRoles(BeanCopyUtil.copy(groupRoleDTO, GroupRoleEntity.class));
        return new R<>(list);
    }

    /**
     * 检查方法
     *
     * @param groupDTO 组信息
     */
    private void check(GroupDTO groupDTO) {
        Assert.hasText(groupDTO.getName(), "用户组名称不能为空");
    }
}
