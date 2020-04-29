package com.pandaz.usercenter.controller;

import com.pandaz.commons.code.RCode;
import com.pandaz.commons.dto.usercenter.AuthMenuDTO;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.custom.CustomProperties;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.service.CaptchaService;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * 通用方法，不校验权限
 *
 * @author Carzer
 * @since 2020-04-27
 */
@RestController
@Slf4j
@RequestMapping("/common")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonController {

    /**
     * 自定义配置
     */
    private final CustomProperties customProperties;

    /**
     * 验证码服务
     */
    private final CaptchaService captchaService;

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 生成验证码
     *
     * @param key      前端上传key
     * @param response response
     * @throws IOException IOException
     */
    @GetMapping(value = "/captcha", produces = "image/png")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        captchaService.create(response, key);
    }

    /**
     * 获取所有授权菜单
     *
     * @return 所有菜单
     */
    @GetMapping("/getAuthMenu")
    public R<List<AuthMenuDTO>> getAuthMenu(@RequestParam String osCode, Principal principal) {
        R<List<String>> roleList = AuthUtil.getRoleListFromPrincipal(principal);
        if (RCode.SUCCESS.getCode() != roleList.getCode()) {
            return new R<>(RCode.getEnum(roleList.getCode()));
        }
        List<MenuEntity> menuList = menuService.getAuthorizedMenu(osCode, roleList.getData());
        return new R<>(BeanCopyUtil.copyList(menuList, AuthMenuDTO.class));
    }
}
