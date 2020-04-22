package com.pandaz.usercenter.controller;

import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.dto.usercenter.UserPwdDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.Result;
import com.pandaz.usercenter.custom.constants.UrlConstants;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author Carzer
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    /**
     * 用户信息服务
     */
    private final UserService userService;

    /**
     * 工具类
     */
    private final ControllerUtil<UserService> controllerUtil;

    /**
     * 根据用户编码获取用户信息
     *
     * @param userDTO 查询条件
     * @return 执行结果
     */
    @GetMapping(UrlConstants.GET)
    public Result<UserDTO> get(@Valid UserDTO userDTO) {
        Result<UserDTO> result = new Result<>();
        try {
            UserDTO dto = BeanCopyUtil.copy(userService.findByCode(userDTO.getCode()), UserDTO.class);
            result.setData(dto);
        } catch (Exception e) {
            log.error("查询方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "查询方法异常"));
        }
        return result;
    }

    /**
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @GetMapping(UrlConstants.PAGE)
    public Result<Map<String, Object>> getPage(UserDTO userDTO) {
        Result<Map<String, Object>> result = new Result<>();
        try {
            result.setData(controllerUtil.getUserPage(userDTO));
        } catch (Exception e) {
            log.error("分页查询异常：", e);
            result.setError(controllerUtil.errorMsg(e, "分页查询异常"));
        }
        return result;
    }

    /**
     * 插入用户信息
     *
     * @param userPwdDTO 用户信息
     * @return 执行结果
     */
    @PostMapping(UrlConstants.INSERT)
    public Result<UserDTO> insert(@Valid @RequestBody UserPwdDTO userPwdDTO, Principal principal) {
        Result<UserDTO> result = new Result<>();
        UserDTO userDTO = userPwdDTO.getUserDTO();
        check(userDTO);
        String password = userPwdDTO.getPassword();
        try {
            UserEntity user = BeanCopyUtil.copy(userDTO, UserEntity.class);
            if (StringUtils.hasText(password)) {
                user.setPassword(password);
            }
            user.setCreatedBy(principal.getName());
            user.setCreatedDate(LocalDateTime.now());
            // 如果没有选择过期时间，就默认6个月后过期
            if (user.getExpireAt() == null) {
                user.setExpireAt(LocalDateTime.now().plusMonths(6L));
            }
            userService.insert(user);
            result.setData(BeanCopyUtil.copy(user, UserDTO.class));
        } catch (Exception e) {
            log.error("插入方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "插入方法异常"));
        }
        return result;
    }

    /**
     * 更新方法
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @PutMapping(UrlConstants.UPDATE)
    public Result<String> update(@Valid @RequestBody UserDTO userDTO, Principal principal) {
        Result<String> result = new Result<>();
        check(userDTO);
        try {
            UserEntity userEntity = BeanCopyUtil.copy(userDTO, UserEntity.class);
            userEntity.setUpdatedBy(principal.getName());
            userEntity.setUpdatedDate(LocalDateTime.now());
            userService.updateByCode(userEntity);
            result.setData("更新成功。");
        } catch (Exception e) {
            log.error("更新方法异常：", e);
            result.setError(controllerUtil.errorMsg(e, "更新方法异常"));
        }
        return result;
    }

    /**
     * 删除方法
     *
     * @param codes     编码
     * @param principal 当前用户
     * @return 执行结果
     */
    @PreAuthorize("!#codes.contains('admin')")
    @DeleteMapping(UrlConstants.DELETE)
    public Result<String> delete(@RequestBody List<String> codes, Principal principal) {
        return controllerUtil.getDeleteResult(userService, principal.getName(), LocalDateTime.now(), codes);
    }

    /**
     * 检查方法
     *
     * @param userDTO 用户信息
     */
    private void check(UserDTO userDTO) {
        Assert.hasText(userDTO.getLoginName(), "登陆名不能为空");
        Assert.hasText(userDTO.getName(), "用户名不能为空");
        Assert.hasText(userDTO.getPhone(), "电话号码不能为空");
    }
}
