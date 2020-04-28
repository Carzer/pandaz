package com.pandaz.usercenter.controller;

import com.pandaz.commons.code.BizCode;
import com.pandaz.commons.dto.usercenter.MenuDTO;
import com.pandaz.commons.exception.BizException;
import com.pandaz.commons.util.BeanCopyUtil;
import com.pandaz.commons.util.CommonUtils;
import com.pandaz.commons.util.R;
import com.pandaz.usercenter.custom.CustomProperties;
import com.pandaz.usercenter.entity.MenuEntity;
import com.pandaz.usercenter.service.CaptchaService;
import com.pandaz.usercenter.service.MenuService;
import com.pandaz.usercenter.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * 工具类
     */
    private final ControllerUtil controllerUtil;

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
    @GetMapping("/getAuthorizedMenu")
    public R<MenuDTO> getAuthorizedMenu(String osCode, Principal principal) {
        List<String> roleList;
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setCode("root");
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            roleList = ((UsernamePasswordAuthenticationToken) principal).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } else if (principal instanceof OAuth2Authentication) {
            roleList = ((OAuth2Authentication) principal).getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        } else {
            throw new BizException(BizCode.AUTH_TYPE_NOT_SUPPORT);
        }
        if (customProperties.isEnableSuperAdmin() && CommonUtils.containsIgnoreCase(roleList, customProperties.getSuperAdminName())) {
            return new R<>(controllerUtil.getAllMenu(menuDTO, true));
        } else {
            List<MenuEntity> menuList = menuService.getAuthorizedMenu(osCode, roleList);
            Map<String, List<MenuEntity>> menuMap = menuList.stream().collect(Collectors.groupingBy(MenuEntity::getParentCode));
            return new R<>(transferToTree(menuDTO, menuMap));
        }
    }

    /**
     * 转换为tree
     *
     * @param menuMap 菜单列表
     * @return tree
     */
    private MenuDTO transferToTree(MenuDTO menuDTO, Map<String, List<MenuEntity>> menuMap) {
        List<MenuEntity> menuList = menuMap.get(menuDTO.getCode());
        if (!CollectionUtils.isEmpty(menuList)) {
            List<MenuDTO> children = menuList.stream().map(entity -> {
                MenuDTO dto = BeanCopyUtil.copy(entity, MenuDTO.class);
                return transferToTree(dto, menuMap);
            }).collect(Collectors.toList());
            menuDTO.setChildren(children);
        }
        return menuDTO;
    }
}
