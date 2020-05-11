package com.github.pandaz.auth.controller;

import com.github.pandaz.auth.custom.CustomProperties;
import com.github.pandaz.auth.custom.constants.SysConstants;
import com.github.pandaz.auth.entity.MenuEntity;
import com.github.pandaz.auth.service.CaptchaService;
import com.github.pandaz.auth.service.MenuService;
import com.github.pandaz.auth.util.AuthUtil;
import com.github.pandaz.auth.util.ServerConfig;
import com.github.pandaz.commons.code.RCode;
import com.github.pandaz.commons.dto.auth.AuthMenuDTO;
import com.github.pandaz.commons.util.BeanCopyUtil;
import com.github.pandaz.commons.util.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
     * 配置
     */
    private final ServerConfig serverConfig;

    /**
     * 验证码服务
     */
    private final CaptchaService captchaService;

    /**
     * 菜单服务
     */
    private final MenuService menuService;

    /**
     * 通用配置
     */
    private final CustomProperties customProperties;

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
        List<String> roles = roleList.getData();
        List<MenuEntity> menuList;
        // 如果开启超级管理员，并且当前用户拥有超级管理员角色，则返回所有菜单并将权限全部放开
        if (customProperties.isEnableSuperAdmin() && roles.contains(customProperties.getSuperAdminName())) {
            menuList = menuService.list();
            menuList.forEach(menuEntity -> menuEntity.setBitResult(SysConstants.TOTAL_DIGIT_RESULT));
        } else {
            menuList = menuService.getAuthorizedMenu(osCode, roles);
        }
        // 无论何种权限，都默认一个404页面
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setCode("404");
        menuEntity.setSorting(0);
        menuEntity.setBitResult(1);
        menuList.add(menuEntity);
        return new R<>(BeanCopyUtil.copyList(menuList, AuthMenuDTO.class));
    }

    @GetMapping("wakeUp")
    private void wake() {
        log.debug("wake up a person who pretends to be asleep");
    }

    /**
     * 应用启动后执行
     */
    public void onStartUp() {
        new RestTemplate().getForEntity(String.format("%s/common/wakeUp", serverConfig.getWakeUrl()), String.class);
    }
}
