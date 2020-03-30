package com.pandaz.usercenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaz.commons.dto.usercenter.GroupDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.ExecuteResult;
import com.pandaz.commons.util.UuidUtil;
import com.pandaz.usercenter.custom.constants.SysConstants;
import com.pandaz.usercenter.entity.GroupEntity;
import com.pandaz.usercenter.service.GroupService;
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
import java.util.HashMap;
import java.util.List;

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
     * 工具类
     */
    private final ControllerUtil<GroupService> controllerUtil;

    /**
     * 查询方法
     *
     * @param groupDTO 查询条件
     * @return 组信息
     */
    @GetMapping
    public ExecuteResult<GroupDTO> get(@Valid GroupDTO groupDTO) {
        ExecuteResult<GroupDTO> result = new ExecuteResult<>();
        try {
            result.setData(BeanCopyUtil.copy(groupService.findByCode(groupDTO.getCode()), GroupDTO.class));
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 分页方法
     *
     * @param groupDTO 查询信息
     * @return 分页信息
     */
    @GetMapping("/getPage")
    public ExecuteResult<HashMap<String, Object>> getPage(GroupDTO groupDTO) {
        ExecuteResult<HashMap<String, Object>> result = new ExecuteResult<>();
        try {
            IPage<GroupEntity> page = groupService.getPage(BeanCopyUtil.copy(groupDTO, GroupEntity.class));
            result.setData(BeanCopyUtil.convertToMap(page, GroupDTO.class));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 新增方法
     *
     * @param groupDTO 组信息
     * @return 组信息
     */
    @PostMapping
    public ExecuteResult<GroupDTO> insert(@RequestBody GroupDTO groupDTO, Principal principal) {
        ExecuteResult<GroupDTO> result = new ExecuteResult<>();
        try {
            check(groupDTO);
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
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param groupDTO 组信息
     * @return 执行结果
     */
    @PutMapping
    public ExecuteResult<String> update(@Valid @RequestBody GroupDTO groupDTO, Principal principal) {
        ExecuteResult<String> result = new ExecuteResult<>();
        try {
            check(groupDTO);
            GroupEntity groupEntity = BeanCopyUtil.copy(groupDTO, GroupEntity.class);
            groupEntity.setUpdatedBy(principal.getName());
            groupEntity.setUpdatedDate(LocalDateTime.now());
            groupService.updateByCode(groupEntity);
            result.setData("更新成功");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes 组信息
     * @return 执行结果
     */
    @DeleteMapping
    public ExecuteResult<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(groupService, principal.getName(), LocalDateTime.now(), codes);
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
