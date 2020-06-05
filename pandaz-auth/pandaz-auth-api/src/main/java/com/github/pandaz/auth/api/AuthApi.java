package com.github.pandaz.auth.api;

import com.github.pandaz.auth.api.fallback.AuthApiFallbackFactory;
import com.github.pandaz.commons.dto.auth.MenuDTO;
import com.github.pandaz.commons.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 权限相关接口
 *
 * @author Carzer
 * @since 2020-06-03
 */
@FeignClient(name = "pandaz-auth-server", fallbackFactory = AuthApiFallbackFactory.class)
public interface AuthApi {

    /**
     * 获取所有菜单
     *
     * @param menuDTO 菜单信息
     * @return 菜单树
     */
    @GetMapping("/menu/getAll")
    R<MenuDTO> getAll(MenuDTO menuDTO);

    /**
     * 根据系统编码及角色编码获取权限列表
     *
     * @param osCode   系统编码
     * @param roleCode 角色编码
     * @return 权限列表
     */
    @GetMapping("/common/getPermissions")
    R<List<String>> getPermissions(@RequestParam String osCode,
                                   @RequestParam String roleCode);

    /**
     * 根据系统编码及角色编码获取权限列表
     *
     * @param osCode 系统编码
     * @return 权限列表
     */
    @GetMapping("/common/getAllPermissions")
    R<Map<String, List<String>>> getAllPermissions(@RequestParam String osCode);
}
