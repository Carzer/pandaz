package com.pandaz.usercenter.controller;

import com.pandaz.commons.constants.UrlConstants;
import com.pandaz.commons.controller.BaseController;
import com.pandaz.commons.dto.usercenter.UserDTO;
import com.pandaz.commons.service.BaseService;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.entity.UserEntity;
import com.pandaz.usercenter.service.UserService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取用户分页信息
     *
     * @param userDTO userDTO
     * @return 执行结果
     */
    @GetMapping(UrlConstants.PAGE)
    @Override
    public R<Map<String, Object>> getPage(UserDTO userDTO) {
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
