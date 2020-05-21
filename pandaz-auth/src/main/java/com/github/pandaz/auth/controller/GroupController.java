package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.entity.GroupEntity;
import com.github.pandaz.auth.entity.GroupRoleEntity;
import com.github.pandaz.auth.entity.UserGroupEntity;
import com.github.pandaz.auth.service.GroupRoleService;
import com.github.pandaz.auth.service.GroupService;
import com.github.pandaz.auth.service.UserGroupService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.*;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "Group", tags = "组信息")
public class GroupController extends BaseController<GroupDTO, GroupEntity> {

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
    private final ControllerUtil controllerUtil;

    /**
     * 获取服务方法
     *
     * @return 服务
     */
    @Override
    protected BaseService<GroupEntity> getBaseService() {
        return this.groupService;
    }

    /**
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @ApiOperation(value = "获取用户分页信息", notes = "获取用户分页信息")
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
    @ApiOperation(value = "绑定用户与组关系", notes = "绑定用户与组关系")
    @PutMapping("/bindGroupMember")
    public R<String> bindGroupMember(@Valid @RequestBody UserGroupDTO userGroupDTO, @ApiIgnore Principal principal) {
        userGroupService.bindGroupMember(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(userGroupDTO, UserGroupEntity.class));
        return R.success();
    }

    /**
     * 列出组内成员
     *
     * @param userGroupDTO 查询条件
     * @return 执行结果
     */
    @ApiOperation(value = "列出组内成员", notes = "列出组内成员")
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
    @ApiOperation(value = "分页方法", notes = "分页方法")
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
    @ApiOperation(value = "绑定用户与组关系", notes = "绑定用户与组关系")
    @PutMapping("/bindRole")
    public R<String> bindRole(@RequestBody GroupRoleDTO groupRoleDTO, @ApiIgnore Principal principal) {
        groupRoleService.bindGroupRole(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(groupRoleDTO, GroupRoleEntity.class));
        return R.success();
    }

    /**
     * 列出绑定的角色
     *
     * @param groupRoleDTO 查询条件
     * @return 执行结果
     */
    @ApiOperation(value = "列出绑定的角色", notes = "列出绑定的角色")
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
    @Override
    protected void check(GroupDTO groupDTO) {
        Assert.hasText(groupDTO.getName(), "用户组名称不能为空");
    }
}
