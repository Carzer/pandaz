package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.custom.log.SysLog;
import com.github.pandaz.auth.entity.UserEntity;
import com.github.pandaz.auth.service.UserService;
import com.github.pandaz.auth.util.ControllerUtil;
import com.github.pandaz.commons.constants.UrlConstants;
import com.github.pandaz.commons.controller.BaseController;
import com.github.pandaz.commons.dto.auth.UserDTO;
import com.github.pandaz.commons.service.BaseService;
import com.github.pandaz.commons.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
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
     * 操作日志记录{@link SysLog}
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @ApiOperation(value = "分页方法", notes = "分页方法")
    @SysLog(value = "用户分页", userIndex = 1)
    @GetMapping(UrlConstants.PAGE)
    @Override
    public R<Map<String, Object>> getPage(UserDTO userDTO, @ApiIgnore Principal principal) {
        return new R<>(controllerUtil.getUserPage(userDTO));
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
