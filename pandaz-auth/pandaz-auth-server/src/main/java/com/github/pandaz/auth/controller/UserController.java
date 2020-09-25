package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.entity.UserOrgEntity;
import com.github.pandaz.auth.service.UserOrgService;
import com.github.pandaz.auth.service.UserService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.annotations.log.OpLog;
import com.github.pandaz.commons.constants.UrlConstants;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.OrganizationDTO;
import com.github.pandaz.commons.dto.auth.UserDTO;
import com.github.pandaz.commons.dto.auth.UserOrgDTO;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 用户信息
 *
 * @author Carzer
 * @since 2019-07-17
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "User", tags = "用户信息")
public class UserController extends BaseController<UserDTO, UserEntity> {

    /**
     * 用户信息服务
     */
    private final UserService userService;

    /**
     * 用户-组织服务
     */
    private final UserOrgService userOrgService;

    /**
     * 工具类
     */
    private final ControllerUtil controllerUtil;

    /**
     * 获取服务方法
     *
     * @return 获取服务
     */
    @Override
    protected BaseService<UserEntity> getBaseService() {
        return this.userService;
    }

    /**
     * 分页方法
     * 操作日志记录{@link OpLog}
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @ApiOperation(value = "分页方法", notes = "分页方法")
    @OpLog(value = "用户分页", userIndex = 1)
    @GetMapping(UrlConstants.PAGE)
    @Override
    public R<Map<String, Object>> getPage(UserDTO userDTO, @ApiIgnore Principal principal) {
        return new R<>(controllerUtil.getUserPage(userDTO));
    }

    /**
     * 获取所有组织
     *
     * @return 所有组织
     */
    @ApiOperation(value = "获取所有组织", notes = "获取所有组织")
    @GetMapping("/getAllOrg")
    public R<OrganizationDTO> getAllOrg(@ApiIgnore Principal principal) {
        return new R<>(controllerUtil.getAllOrg());
    }

    /**
     * 绑定组织方法
     *
     * @param userOrgDTO 绑定信息
     * @param principal  用户信息
     * @return 执行结果
     */
    @ApiOperation(value = "绑定组织", notes = "绑定组织方法")
    @PostMapping("/joinOrg")
    public R<String> joinOrg(@Valid @RequestBody UserOrgDTO userOrgDTO, @ApiIgnore Principal principal) {
        userOrgService.bindUserOrg(principal.getName(), LocalDateTime.now(), BeanCopyUtil.copy(userOrgDTO, UserOrgEntity.class));
        return R.success();
    }

    /**
     * 获取已绑定组织
     *
     * @param userOrgDTO 查询条件
     * @param principal  当前用户
     * @return 查询结果
     */
    @ApiOperation(value = "获取已绑定组织组织", notes = "获取已绑定组织组织")
    @GetMapping("/getUserOrg")
    public R<List<String>> getUserOrg(UserOrgDTO userOrgDTO, @ApiIgnore Principal principal) {
        List<String> userOrg = userOrgService.listBindUserOrg(BeanCopyUtil.copy(userOrgDTO, UserOrgEntity.class));
        return new R<>(userOrg);
    }

    /**
     * 检查方法
     *
     * @param userDTO 用户信息
     */
    @Override
    protected void check(UserDTO userDTO) {
        Assert.hasText(userDTO.getLoginName(), "登陆名不能为空");
        Assert.hasText(userDTO.getName(), "用户名不能为空");
        Assert.hasText(userDTO.getPhone(), "电话号码不能为空");
    }
}
