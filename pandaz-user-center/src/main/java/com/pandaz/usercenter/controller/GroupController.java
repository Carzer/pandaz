package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.*;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
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
    public ExecuteResult<GroupDTO> get(@Valid GroupDTO groupDTO) {
        ExecuteResult<GroupDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(groupService.findByCode(groupDTO.getCode()), GroupDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "查询方法异常"));
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param groupDTO 查询信息
     * @return 分页信息
     */
    @GetMapping(UrlConstants.PAGE)
    public ExecuteResult<Map<String, Object>> getPage(GroupDTO groupDTO) {
        ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<GroupEntity> page = groupService.getPage(BeanCopyUtil.copy(groupDTO, GroupEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, GroupDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param groupDTO 组信息
     * @return 组信息
     */
    @PostMapping(UrlConstants.INSERT)
    public ExecuteResult<GroupDTO> insert(@RequestBody GroupDTO groupDTO, Principal principal) {
        ExecuteResult<GroupDTO> result = new ExecuteResult<>();
        check(groupDTO);
        try {
            GroupEntity groupEntity = BeanCopyUtil.copy(groupDTO, GroupEntity.class);
            groupEntity.setId(UuidUtil.getId());
            if (StringUtils.hasText(groupEntity.getCode())) {
                groupEntity.setCode(String.format("%s%s", SysConstants.GROUP_PREFIX, groupEntity.getCode()));
            }
            groupEntity.setCreatedBy(principal.getName());
            groupEntity.setCreatedDate(LocalDateTime.now());
            groupService.insert(groupEntity);
            result.setData(BeanCopyUtil.copy(groupEntity, groupDTO));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "插入方法异常"));
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param groupDTO 组信息
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public ExecuteResult<String> update(@Valid @RequestBody GroupDTO groupDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        check(groupDTO);
        try {
            GroupEntity groupEntity = BeanCopyUtil.copy(groupDTO, GroupEntity.class);
            groupEntity.setUpdatedBy(principal.getName());
            groupEntity.setUpdatedDate(LocalDateTime.now());
            groupService.updateByCode(groupEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "更新方法异常"));
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping(UrlConstants.DELETE)
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(groupService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @GetMapping("/getUserPage")
    public ExecuteResult<Map<String, Object>> getUserPage(UserDTO userDTO) {
        ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.getUserPage(userDTO));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 绑定用户与组关系
     *
     * @param userGroupDTO 用户-组信息
     * @return 执行结果
     */
    @PutMapping("/bindGroupMember")
    public ExecuteResult<String> bindGroupMember(@Valid @RequestBody UserGroupDTO userGroupDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            userGroupService.bindGroupMember(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(userGroupDTO, UserGroupEntity.class));
            result.setData("绑定成功");
        } catch (Exception e) {
            log.error("绑定及用户组异常：", e);
            result.setError(controllerUtil.errorMsg(e, "绑定及用户组异常"));
        }
        return result;
    }

    /**
     * 列出组内成员
     *
     * @param userGroupDTO 查询条件
     * @return 执行结果
     */
    @GetMapping("/listBindGroupMembers")
    public ExecuteResult<List<String>> listBindGroupMembers(UserGroupDTO userGroupDTO) {
        ExecuteResult<List<String>> result = new ExecuteResult<>();
        try {
            List<String> list = userGroupService.listBindGroupMembers(BeanCopyUtil.copy(userGroupDTO, UserGroupEntity.class));
            result.setData(list);
        } catch (Exception e) {
            log.error("列出组内成员异常：", e);
            result.setError(controllerUtil.errorMsg(e, "列出组内成员异常"));
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param roleDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getRolePage")
    public ExecuteResult<Map<String, Object>> getRolePage(RoleDTO roleDTO) {
        ExecuteResult<Map<String, Object>> result = new ExecuteResult<>();
        try {
            result.setData(controllerUtil.getRolePage(roleDTO));
        } catch (Exception e) {
            log.error("获取角色分页异常：", e);
            result.setError(controllerUtil.errorMsg(e, "获取角色分页异常"));
        }
        return result;
    }

    /**
     * 绑定用户与组关系
     *
     * @param groupRoleDTO 组-角色信息
     * @return 执行结果
     */
    @PutMapping("/bindRole")
    public ExecuteResult<String> bindRole(@RequestBody GroupRoleDTO groupRoleDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            groupRoleService.bindGroupRole(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(groupRoleDTO, GroupRoleEntity.class));
            result.setData("绑定成功");
        } catch (Exception e) {
            log.error("绑定角色异常：", e);
            result.setError(controllerUtil.errorMsg(e, "绑定角色异常"));
        }
        return result;
    }

    /**
     * 列出绑定的角色
     *
     * @param groupRoleDTO 查询条件
     * @return 执行结果
     */
    @GetMapping("/listBindRoles")
    public ExecuteResult<List<String>> listBindRoles(GroupRoleDTO groupRoleDTO) {
        ExecuteResult<List<String>> result = new ExecuteResult<>();
        try {
            List<String> list = groupRoleService.listBindGroupRoles(BeanCopyUtil.copy(groupRoleDTO, GroupRoleEntity.class));
            result.setData(list);
        } catch (Exception e) {
            log.error("列出绑定角色异常：", e);
            result.setError(controllerUtil.errorMsg(e, "列出绑定角色异常"));
        }
        return result;
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
